package com.hanghae.concert.domain.concert.dto;

import com.hanghae.concert.domain.concert.*;

public record SaveConcertDto(
        String title,
        Integer capacity,
        Integer seatPrice,
        ConcertStatus concertStatus
) {
}
