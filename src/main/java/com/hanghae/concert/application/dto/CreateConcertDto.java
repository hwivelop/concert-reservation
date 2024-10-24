package com.hanghae.concert.application.dto;

import com.hanghae.concert.domain.concert.*;

import java.time.*;

public record CreateConcertDto(
        String title,
        Integer capacity,
        Integer seatPrice,
        ConcertStatus concertStatus,
        LocalDateTime startAt
) {
}
