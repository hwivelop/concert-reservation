package com.hanghae.concert.application.dto;

import com.hanghae.concert.domain.member.*;

public record MemberDto(
        Long memberId,
        Integer balance
) {
    public static MemberDto of(Member member) {

        return new MemberDto(
                member.getId(),
                member.getBalance()
        );
    }
}
