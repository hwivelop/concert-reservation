package com.hanghae.concert.api.member.dto.response;

import com.hanghae.concert.application.dto.*;

public record MemberResponse(
        Long memberId,
        Integer balance
) {
    public static MemberResponse toResponse(MemberDto memberDto) {

        return new MemberResponse(
                memberDto.memberId(),
                memberDto.balance()
        );
    }
}
