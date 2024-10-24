package com.hanghae.concert.api.queue.dto.response;

import com.hanghae.concert.domain.member.queue.dto.*;

import java.time.*;

public record MemberQueueCreateResponse(
        Long memberId,
        String token,
        LocalDateTime expiredAt
) {

    public static MemberQueueCreateResponse toResponse(MemberQueueDto memberQueueDto) {

        if (memberQueueDto == null) {
            return null;
        }
        return new MemberQueueCreateResponse(
                memberQueueDto.memberId(),
                memberQueueDto.token(),
                memberQueueDto.expiredAt()
        );
    }
}
