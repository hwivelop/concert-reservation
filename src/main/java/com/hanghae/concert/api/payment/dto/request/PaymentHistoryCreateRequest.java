package com.hanghae.concert.api.payment.dto.request;

public record PaymentHistoryCreateRequest(
        Long memberId,
        Long amount
) {
}
