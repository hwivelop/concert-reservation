package com.hanghae.concert.api.queue.dto.response;

import java.time.*;

public record MemberQueueCreateResponse(
        Long memberId,
        String token,
        LocalDateTime expiredAt
) {
}
