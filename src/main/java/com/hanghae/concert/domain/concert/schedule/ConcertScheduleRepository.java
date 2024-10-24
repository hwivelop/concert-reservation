package com.hanghae.concert.domain.concert.schedule;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Long> {

    List<ConcertSchedule> findAllByConcertId(Long concertId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sch FROM ConcertSchedule sch WHERE sch.id = :concertScheduleId")
    Optional<ConcertSchedule> findByIdWithLock(
            @Param("concertScheduleId") Long concertScheduleId
    );
}