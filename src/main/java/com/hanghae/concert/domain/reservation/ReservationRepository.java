package com.hanghae.concert.domain.reservation;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByConcertSeatIdAndReservationStatus(Long concertSeatId, ReservationStatus reservationStatus);
}