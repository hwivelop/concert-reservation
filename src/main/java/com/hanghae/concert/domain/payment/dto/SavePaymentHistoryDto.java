package com.hanghae.concert.domain.payment.dto;

import com.hanghae.concert.domain.payment.*;

public record SavePaymentHistoryDto(
        Long id,
        Long memberId,
        Long reservationId,
        PaymentType paymentType,
        Long amount
) {
}
