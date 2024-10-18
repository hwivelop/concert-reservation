package com.hanghae.concert.domain.payment.dto;

import com.hanghae.concert.domain.payment.*;

public record PaymentHistoryDto(
        Long id,
        Long memberId,
        Long reservationId,
        PaymentType paymentType
) {

    public static PaymentHistoryDto of(PaymentHistory paymentHistory) {

        return new PaymentHistoryDto(
                paymentHistory.getMemberId(),
                paymentHistory.getMemberId(),
                paymentHistory.getReservationId(),
                paymentHistory.getPaymentType()
        );
    }
}
