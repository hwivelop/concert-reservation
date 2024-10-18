package com.hanghae.concert.domain.concert.dto;

import com.hanghae.concert.domain.concert.*;

public record ConcertDto(
        Long concertId,
        String title,
        Integer capacity,
        Integer seatPrice,
        ConcertStatus concertStatus
) {
    public static ConcertDto of(Concert concert) {

        if (concert == null) return null;

        return new ConcertDto(
                concert.getId(),
                concert.getTitle(),
                concert.getCapacity(),
                concert.getSeatPrice(),
                concert.getConcertStatus()
        );
    }
}
