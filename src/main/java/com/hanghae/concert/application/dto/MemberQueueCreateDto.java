package com.hanghae.concert.application.dto;

public record MemberQueueCreateDto(
        Long memberId,
        Long concertId
) {
}
