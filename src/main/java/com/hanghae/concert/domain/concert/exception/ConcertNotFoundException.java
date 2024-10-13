package com.hanghae.concert.domain.concert.exception;


import com.hanghae.concert.api.common.exception.*;

public class ConcertNotFoundException extends NotFoundException {

    public ConcertNotFoundException() {
        super("콘서트 정보가 존재하지 않습니다");
    }
}
