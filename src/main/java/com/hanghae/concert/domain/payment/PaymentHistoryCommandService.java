package com.hanghae.concert.domain.payment;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.dto.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.payment.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentHistoryCommandService {

    private final PaymentHistoryRepository paymentHistoryRepository;


    public PaymentHistoryDto savePaymentHistory(SavePaymentHistoryDto dto) {

        PaymentHistory paymentHistory = paymentHistoryRepository.save(
                new PaymentHistory(
                        null,
                        dto.memberId(),
                        dto.reservationId(),
                        dto.paymentType(),
                        dto.amount()
                )
        );

        return PaymentHistoryDto.of(paymentHistory);
    }
}
