package com.hanghae.concert.api.concert.dto.request;

public record ReservationSeatRequest(
        Long concertId,
        Long concertScheduleId,
        Long reservationId,
        Integer seatNumber
) {
}
