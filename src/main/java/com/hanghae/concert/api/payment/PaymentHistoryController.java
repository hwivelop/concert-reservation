package com.hanghae.concert.api.payment;

import com.hanghae.concert.api.payment.dto.request.*;
import com.hanghae.concert.api.payment.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.hanghae.concert.domain.payment.PaymentType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-history")
public class PaymentHistoryController {

    /**
     * 유저의 잔액 사용 히스토리 저장
     */
    @Operation(summary = "유저의 잔액 사용 히스토리 저장", description = "회원의 잔액 사용 또는 충전 내역을 저장합니다.")
    @PostMapping
    public ResponseEntity<List<PaymentHistoryResponse>> createPaymentHistory(
            @Parameter(description = "충전 또는 사용 금액", required = true)
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