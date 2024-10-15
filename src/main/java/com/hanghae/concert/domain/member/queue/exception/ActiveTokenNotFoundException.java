package com.hanghae.concert.domain.member.queue.exception;


import com.hanghae.concert.api.common.exception.*;

public class ActiveTokenNotFoundException extends NotFoundException {

    public ActiveTokenNotFoundException() {
        super("입장 가능한 토큰이 존재하지 않습니다");
    }
}
