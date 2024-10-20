package com.hanghae.concert.api.payment;

import com.hanghae.concert.api.payment.dto.request.*;
import com.hanghae.concert.api.payment.dto.response.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.hanghae.concert.domain.payment.PaymentType.*;

public class PaymentHistoryController {

    /**
     * 유저의 잔액 사용 히스토리 저장
     */
    @PostMapping
    public ResponseEntity<List<PaymentHistoryResponse>> createPaymentHistory(
            @RequestBody PaymentHistoryCreateRequest request
    ) {

        List<PaymentHistoryResponse> responses = List.of(
                new PaymentHistoryResponse(1L, CHARGE, 1000L),
                new PaymentHistoryResponse(1L, USE, 1000L),
                new PaymentHistoryResponse(1L, CHARGE, 1000L)
        );

        return ResponseEntity.ok(responses);
    }
}