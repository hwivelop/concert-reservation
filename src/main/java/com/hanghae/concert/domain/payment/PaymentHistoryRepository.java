package com.hanghae.concert.domain.payment;

import org.springframework.data.jpa.repository.*;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}