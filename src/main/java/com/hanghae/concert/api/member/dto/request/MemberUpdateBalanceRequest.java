package com.hanghae.concert.api.member.dto.request;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.payment.*;

public record MemberUpdateBalanceRequest(
        Long memberId,
        Integer amount,
        PaymentType paymentType
) {
    public MemberUpdateBalanceDto toApplication() {
        return new MemberUpdateBalanceDto(this.memberId, this.amount, this.paymentType);
    }
}
