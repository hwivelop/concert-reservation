package com.hanghae.concert.domain.concert.seat.exception;


import com.hanghae.concert.api.common.exception.*;

public class ConcertSeatNotFoundException extends NotFoundException {

    public ConcertSeatNotFoundException() {
        super("좌석 정보가 존재하지 않습니다.");
    }
}
