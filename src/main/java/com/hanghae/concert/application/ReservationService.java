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
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final MemberQueryService memberQueryService;
    private final MemberQueueQueryService memberQueueQueryService;
    private final ConcertQueryService concertQueryService;
    private final ConcertScheduleQueryService concertScheduleQueryService;
    private final ConcertSeatQueryService concertSeatQueryService;

    public List<ConcertScheduleDto> searchAvailableDates(Long memberId, Long concertId) {

        validateMemberAndConcert(memberId, concertId);

        return concertScheduleQueryService.getAvailableConcertSchedules(concertId);

    }

    public List<ConcertSeatDto> searchReservedSeats(Long memberId, Long concertId, Long concertScheduleId) {

        validateMemberAndConcert(memberId, concertId);

        return concertSeatQueryService.getReservedSeats(concertScheduleId);

    }

    private void validateMemberAndConcert(Long memberId, Long concertId) {

        // 유저 검증
        if (memberQueryService.existsMemberById(memberId)) {
            throw new MemberNotFoundException();
        }

        // 토큰 검증
        if (memberQueueQueryService.isAvailableToken(memberId, concertId)) {
            throw new ActiveTokenNotFoundException();
        }

        // 콘서트 검증
        if (!concertQueryService.existsById(concertId)) {
            throw new ConcertNotFoundException();
        }
    }
}
