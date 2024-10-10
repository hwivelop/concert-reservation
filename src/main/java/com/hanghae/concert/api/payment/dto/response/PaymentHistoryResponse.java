package com.hanghae.concert.api.payment.dto.response;

import com.hanghae.concert.domain.payment.*;

public record PaymentHistoryResponse(
        Long memberId,
        PaymentType paymentType,
        Long amount
) {
}
