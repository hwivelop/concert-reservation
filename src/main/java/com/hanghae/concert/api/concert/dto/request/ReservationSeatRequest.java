package com.hanghae.concert.api.concert.dto.request;

public record ReservationSeatRequest(
        Long memberId,
        Long concertId,
        Long concertScheduleId,
        Integer seatNumber
) {
}
