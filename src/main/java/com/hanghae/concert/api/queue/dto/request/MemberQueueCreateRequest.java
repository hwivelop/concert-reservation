package com.hanghae.concert.api.queue.dto.request;

import com.hanghae.concert.application.dto.*;

public record MemberQueueCreateRequest(
        Long memberId,
        Long concertId
) {

    public MemberQueueCreateDto toRequest() {

        return new MemberQueueCreateDto(
                memberId,
                concertId
        );
    }
}
