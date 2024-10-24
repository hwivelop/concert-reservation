package com.hanghae.concert.api.concert.dto.response;

import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.reservation.*;

public record ReservationPayResponse(
        Long concertId,
        ReservationStatus reservationStatus,
        TokenStatus tokenStatus,
        Integer price
) {

    public static ReservationPayResponse toResponse(ReservationPayDto payDto) {

        return new ReservationPayResponse(
                payDto.concertId(),
                payDto.reservationStatus(),
                payDto.tokenStatus(),
                payDto.price()
        );
    }
}
