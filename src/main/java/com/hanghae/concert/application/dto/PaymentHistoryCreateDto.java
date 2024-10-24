package com.hanghae.concert.application.dto;

public record PaymentHistoryCreateDto(
        Long memberId,
        Long concertId,
        Long reservationId
) {
}
