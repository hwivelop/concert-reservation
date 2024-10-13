package com.hanghae.concert.domain.member.dto;

import com.hanghae.concert.domain.member.*;

public record MemberDto(
        Long id,
        Long balance
) {
    public static MemberDto of(Member member) {

        if (member == null) return null;

        return new MemberDto(
                member.getId(),
                member.getBalance()
        );
    }
}
