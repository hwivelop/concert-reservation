package com.hanghae.concert.domain.payment;

import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.dto.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.payment.dto.*;
import com.hanghae.concert.domain.payment.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentHistoryQueryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryDto getPaymentHistoryById(Long paymentHistoryId) {

        PaymentHistory paymentHistory = paymentHistoryRepository.findById(paymentHistoryId)
                .orElseThrow(PaymentHistoryNotFoundException::new);

        return PaymentHistoryDto.of(paymentHistory);
    }

    public Boolean existsPaymentHistoryById(Long paymentHistoryId) {

        return paymentHistoryRepository.existsById(paymentHistoryId);
    }
}
