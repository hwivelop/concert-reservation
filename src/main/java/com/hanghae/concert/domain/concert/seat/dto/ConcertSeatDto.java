package com.hanghae.concert.domain.concert.seat.dto;

import com.hanghae.concert.domain.concert.seat.*;

public record ConcertSeatDto(
        Long id,
        Long concertScheduleId,
        Integer seatNumber,
        Boolean isReserved
) {

    public static ConcertSeatDto of(ConcertSeat concertSeat) {

        if (concertSeat == null) return null;

        return new ConcertSeatDto(
                concertSeat.getId(),
                concertSeat.getConcertScheduleId(),
                concertSeat.getSeatNumber(),
                concertSeat.getIsReserved()
        );
    }
}