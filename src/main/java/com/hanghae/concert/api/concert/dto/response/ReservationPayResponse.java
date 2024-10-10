package com.hanghae.concert.api.concert.dto.response;

public record ReservationPayResponse(
        Long concertId,
        Long concertScheduleId,
        Integer seatNumber,
        Long price
) {
}
