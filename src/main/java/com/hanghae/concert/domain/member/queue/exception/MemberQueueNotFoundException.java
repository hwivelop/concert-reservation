package com.hanghae.concert.domain.member.queue.exception;


import com.hanghae.concert.api.common.exception.*;
import org.aspectj.weaver.ast.*;

public class MemberQueueNotFoundException extends NotFoundException {

    public MemberQueueNotFoundException() {
        super("토큰이 존재하지 않습니다");
    }
}
