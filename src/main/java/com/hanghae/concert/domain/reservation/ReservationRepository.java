package com.hanghae.concert.domain.reservation;

import org.springframework.data.jpa.repository.*;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}