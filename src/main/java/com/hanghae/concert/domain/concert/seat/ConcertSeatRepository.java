package com.hanghae.concert.domain.concert.seat;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Long> {

    List<ConcertSeat> findAllByConcertScheduleId(Long concertScheduleId);

    Optional<ConcertSeat> findByConcertScheduleIdAndSeatNumber(Long concertScheduleId, int seatNumber);
}