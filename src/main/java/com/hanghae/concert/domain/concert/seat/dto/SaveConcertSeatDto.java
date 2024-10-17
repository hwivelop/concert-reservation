package com.hanghae.concert.domain.concert.seat.dto;

public record SaveConcertSeatDto(
        Long concertScheduleId,
        Integer seatNumber
) {
}
