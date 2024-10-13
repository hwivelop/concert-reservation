package com.hanghae.concert.domain.reservation;

import com.hanghae.concert.domain.payment.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public Reservation getReservationById(Long reservationId) {

        return reservationRepository.findById(reservationId)
                .orElseThrow(PaymentHistoryNotFoundException::new);
    }
}
