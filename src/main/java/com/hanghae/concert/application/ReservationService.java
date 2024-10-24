package com.hanghae.concert.application;

import com.hanghae.concert.api.concert.dto.request.*;
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
import org.springframework.transaction.annotation.*;

import java.util.*;

import static com.hanghae.concert.domain.reservation.ReservationStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final MemberQueryService memberQueryService;
    private final MemberQueueQueryService memberQueueQueryService;
    private final ConcertQueryService concertQueryService;
    private final ConcertScheduleQueryService concertScheduleQueryService;
    private final ConcertSeatQueryService concertSeatQueryService;
    private final ConcertSeatCommandService concertSeatCommandService;
    private final ReservationQueryService reservationQueryService;
    private final ReservationCommandService reservationCommandService;


    public List<ConcertScheduleDto> searchAvailableDates(Long memberId, Long concertId) {

        validateMemberAndConcert(memberId, concertId);

        return concertScheduleQueryService.getAvailableConcertSchedules(concertId);

    }

    public List<ConcertSeatDto> searchReservedSeats(Long memberId, Long concertId, Long concertScheduleId) {

        validateMemberAndConcert(memberId, concertId);

        return concertSeatQueryService.getReservedSeats(concertScheduleId);

    }

    @Transactional
    public ReservationDto tempReservation(ReservationSeatRequest request) {

        Long memberId = request.memberId();
        Long concertScheduleId = request.concertScheduleId();
        Integer seatNumber = request.seatNumber();

        validateMemberAndConcert(memberId, request.concertId());

        ConcertSchedule concertSchedule = concertScheduleQueryService.getConcertSchedule(concertScheduleId);

        // seat 생성
        ConcertSeat concertSeat = concertSeatQueryService.getConcertSeat(concertScheduleId, seatNumber)
                .orElseGet(() -> concertSeatCommandService.saveConcertSeat(
                        new SaveConcertSeatDto(concertScheduleId, seatNumber)
                ));

        // 잔여좌석 업데이트
        concertSchedule.changeRemainingSeat(true);

        Concert concert = concertQueryService.getConcertById(concertSchedule.getConcertId());

        // 정원이 다차면 concert 도 sold_out 상태로 변경
        if (concertSchedule.getRemainingSeat() == 0) {
            concert.changeStatus(ConcertStatus.SOLD_OUT);
        }

        //reservation 테이블 temp_reservation insert
        Optional<Reservation> reservation = reservationQueryService.getReservationBySeatIdAndStatus(concertSeat.getId(), TEMP_RESERVED);

        return reservation.map(ReservationDto::of).orElseGet(() -> ReservationDto.of(
                reservationCommandService.save(
                        new Reservation(null, memberId, concertSeat.getId(), TEMP_RESERVED, concert.getSeatPrice())
                )
        ));

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
