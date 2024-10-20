package com.hanghae.concert.api.payment;

import com.hanghae.concert.api.payment.dto.request.*;
import com.hanghae.concert.api.payment.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public interface IPaymentHistoryController {

    /**
     * 유저의 잔액 사용 히스토리 저장
     */
    @Operation(summary = "유저의 잔액 사용 히스토리 저장", description = "회원의 잔액 사용 또는 충전 내역을 저장합니다.")
    ResponseEntity<List<PaymentHistoryResponse>> createPaymentHistory(
            @Parameter(description = "충전 또는 사용 금액", required = true)
            @RequestBody PaymentHistoryCreateRequest request
    );
}