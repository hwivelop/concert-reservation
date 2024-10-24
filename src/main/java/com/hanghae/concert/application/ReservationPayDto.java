package com.hanghae.concert.application;

import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.reservation.*;

public record ReservationPayDto(
        Long concertId,
        ReservationStatus reservationStatus,
        TokenStatus tokenStatus,
        Integer price
) {
}
