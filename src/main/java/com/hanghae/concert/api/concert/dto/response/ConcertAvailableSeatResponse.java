package com.hanghae.concert.api.concert.dto.response;

public record ConcertAvailableSeatResponse(
        Long concertId,
        Long concertScheduleId,
        Integer seatNumber
) {
}
