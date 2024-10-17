package com.hanghae.concert.domain.concert.schedule.exception;


import com.hanghae.concert.api.common.exception.*;

public class ConcertScheduleNotFoundException extends NotFoundException {

    public ConcertScheduleNotFoundException() {
        super("콘서트 상세 정보가 존재하지 않습니다");
    }
}
