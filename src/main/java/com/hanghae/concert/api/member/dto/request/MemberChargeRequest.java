package com.hanghae.concert.api.member.dto.request;

public record MemberChargeRequest(
        Long memberId,
        Long amount
) {
}
