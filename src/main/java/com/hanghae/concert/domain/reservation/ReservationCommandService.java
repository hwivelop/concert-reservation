package com.hanghae.concert.domain.reservation;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;


    public Reservation save(Reservation reservation) {

       return reservationRepository.save(reservation);
    }
}
