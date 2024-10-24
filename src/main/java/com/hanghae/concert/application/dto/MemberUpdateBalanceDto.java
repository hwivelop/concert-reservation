package com.hanghae.concert.application.dto;

import com.hanghae.concert.domain.payment.*;

public record MemberUpdateBalanceDto(
        Long memberId,
        Integer amount,
        PaymentType paymentType
) {
}
