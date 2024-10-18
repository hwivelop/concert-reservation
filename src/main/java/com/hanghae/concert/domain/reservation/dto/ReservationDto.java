package com.hanghae.concert.domain.reservation.dto;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.reservation.*;

public record ReservationDto(
        Long reservationId,
        Long memberId,
        Long concertSeatId,
        ReservationStatus reservationStatus,
        Integer reservationPrice
) {
    public static ReservationDto of(Reservation reservation) {

        if (reservation == null) return null;

        return new ReservationDto(
                reservation.getId(),
                reservation.getMemberId(),
                reservation.getConcertSeatId(),
                reservation.getReservationStatus(),
                reservation.getReservationPrice()
        );
    }
}
