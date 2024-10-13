package com.hanghae.concert.domain.payment.exception;


import com.hanghae.concert.api.common.exception.*;

public class PaymentHistoryNotFoundException extends NotFoundException {

    public PaymentHistoryNotFoundException() {
        super("결제 히스토리 정보가 존재하지 않습니다");
    }
}
