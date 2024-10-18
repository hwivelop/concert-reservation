package com.hanghae.concert.domain.reservation;

import com.hanghae.concert.domain.payment.*;
import com.hanghae.concert.domain.payment.dto.*;
import com.hanghae.concert.domain.reservation.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;


    public Reservation saveReservation(Reservation reservation) {

       return reservationRepository.save(reservation);
    }
}
