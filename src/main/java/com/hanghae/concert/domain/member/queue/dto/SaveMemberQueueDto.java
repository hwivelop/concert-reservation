package com.hanghae.concert.domain.member.queue.dto;

import com.hanghae.concert.domain.member.queue.*;

import java.time.*;

public record SaveMemberQueueDto(
        Long memberId,
        String token
) {
}
