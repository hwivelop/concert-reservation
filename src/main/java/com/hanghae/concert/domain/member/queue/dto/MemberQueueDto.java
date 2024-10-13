package com.hanghae.concert.domain.member.queue.dto;

import com.hanghae.concert.domain.member.queue.*;

import java.time.*;

public record MemberQueueDto(
        Long memberId,
        String token,
        TokenStatus tokenStatus,
        LocalDateTime expiredAt
) {
    public static MemberQueueDto of(MemberQueue memberQueue) {

        return new MemberQueueDto(
                memberQueue.getMemberId(),
                memberQueue.getToken(),
                memberQueue.getTokenStatus(),
                memberQueue.getExpiredAt()
        );
    }
}
