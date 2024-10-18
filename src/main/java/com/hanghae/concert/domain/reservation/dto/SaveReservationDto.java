package com.hanghae.concert.domain.reservation.dto;

import com.hanghae.concert.domain.reservation.*;

public record SaveReservationDto(
        Long memberId,
        Long concertSeatId,
        ReservationStatus reservationStatus,
        Integer reservationPrice
) {
}
