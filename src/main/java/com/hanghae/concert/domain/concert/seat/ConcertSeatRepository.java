package com.hanghae.concert.domain.concert.seat;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Long> {

    List<ConcertSeat> findAllByConcertScheduleId(Long concertScheduleId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cs FROM ConcertSeat cs WHERE cs.id = :concertScheduleId and cs.seatNumber = :seatNumber")
    Optional<ConcertSeat> findByConcertScheduleIdAndSeatNumberWithLock(
            @Param("concertScheduleId") Long concertScheduleId,
            @Param("seatNumber") int seatNumber
    );
}