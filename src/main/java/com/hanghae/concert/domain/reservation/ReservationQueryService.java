package com.hanghae.concert.domain.reservation;

import com.hanghae.concert.domain.payment.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public Reservation getReservationById(Long reservationId) {

        return reservationRepository.findById(reservationId)
                .orElseThrow(PaymentHistoryNotFoundException::new);
    }

    public Optional<Reservation> getReservationBySeatIdAndStatus(Long concertSeatId, ReservationStatus reservationStatus) {

        return reservationRepository.findByConcertSeatIdAndReservationStatus(concertSeatId, reservationStatus);
    }
}
