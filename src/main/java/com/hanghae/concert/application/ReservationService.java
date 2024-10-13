package com.hanghae.concert.application;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.exception.*;
import com.hanghae.concert.domain.concert.schedule.*;
import com.hanghae.concert.domain.concert.schedule.dto.*;
import com.hanghae.concert.domain.concert.seat.*;
import com.hanghae.concert.domain.concert.seat.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.member.queue.exception.*;
import com.hanghae.concert.domain.reservation.*;
import com.hanghae.concert.domain.reservation.dto.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.hanghae.concert.domain.reservation.ReservationStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final MemberQueryService memberQueryService;
    private final MemberQueueQueryService memberQueueQueryService;
    private final ConcertQueryService concertQueryService;
    private final ConcertCommandService concertCommandService;
    private final ConcertScheduleQueryService concertScheduleQueryService;
    private final ConcertScheduleCommandService concertScheduleCommandService;
    private final ConcertSeatQueryService concertSeatQueryService;
    private final ConcertSeatCommandService concertSeatCommandService;
    private final ReservationCommandService reservationCommandService;


    public List<ConcertScheduleDto> searchAvailableDates(Long memberId, Long concertId) {

        validateMemberAndConcert(memberId, concertId);

        return concertScheduleQueryService.getAvailableConcertSchedules(concertId);

    }

    public List<ConcertSeatDto> searchReservedSeats(Long memberId, Long concertId, Long concertScheduleId) {

        validateMemberAndConcert(memberId, concertId);

        return concertSeatQueryService.getReservedSeats(concertScheduleId);

    }

    public ReservationDto tempReservation(Long memberId, Long concertId, Long concertScheduleId, int seatNumber) {

        validateMemberAndConcert(memberId, concertId);

        ConcertSchedule concertSchedule = concertScheduleQueryService.getConcertSchedule(concertScheduleId);

        // seat 생성
        ConcertSeat concertSeat = concertSeatQueryService.getConcertSeat(concertScheduleId, seatNumber)
                .orElseGet(() -> concertSeatCommandService.saveConcertSeat(
                        new SaveConcertSeatDto(concertScheduleId, seatNumber)
                ));

        // 잔여좌석 업데이트
        concertSchedule.changeRemainingSeat(true);
        concertScheduleCommandService.save(concertSchedule);

        Concert concert = concertQueryService.getConcertById(concertSchedule.getConcertId());

        // 정원이 다차면 concert 도 sold_out 상태로 변경
        if (concertSchedule.getRemainingSeat() == 0) {
            concert.changeStatus(ConcertStatus.SOLD_OUT);
            concertCommandService.save(concert);
        }

        //reservation 테이블 temp_reservation insert
        Reservation reservation = reservationCommandService.save(
                new Reservation(null, memberId, concertSeat.getId(), TEMP_RESERVED, concert.getSeatPrice())
        );

        return ReservationDto.of(reservation);
    }

    private void validateMemberAndConcert(Long memberId, Long concertId) {

        // 유저 검증
        if (!memberQueryService.existsMemberById(memberId)) {
            throw new MemberNotFoundException();
        }

        // 토큰 검증
        if (!memberQueueQueryService.isAvailableToken(memberId, concertId)) {
            throw new ActiveTokenNotFoundException();
        }

        // 콘서트 검증
        if (!concertQueryService.existsById(concertId)) {
            throw new ConcertNotFoundException();
        }
    }
}
