package com.hanghae.concert.domain.concert.schedule.dto;

import com.hanghae.concert.domain.concert.schedule.*;

import java.time.*;

public record ConcertScheduleDto(
        Long id,
        Long concertId,
        Integer remainingSeat,
        LocalDateTime startAt
) {

    public static ConcertScheduleDto of(ConcertSchedule concertSchedule) {

        if (concertSchedule == null) return null;

        return new ConcertScheduleDto(
                concertSchedule.getId(),
                concertSchedule.getConcertId(),
                concertSchedule.getRemainingSeat(),
                concertSchedule.getStartAt()
        );
    }
}