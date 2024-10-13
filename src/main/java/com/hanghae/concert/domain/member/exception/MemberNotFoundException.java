package com.hanghae.concert.domain.member.exception;


import com.hanghae.concert.api.common.exception.*;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super("고객 정보가 존재하지 않습니다");
    }
}
