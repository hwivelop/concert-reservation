package com.hanghae.concert.domain.reservation.exception;


import com.hanghae.concert.api.common.exception.*;

public class ExpiredReservationException extends BusinessException {

    public ExpiredReservationException() {
        super("만료된 예약 입니다.");
    }
}
