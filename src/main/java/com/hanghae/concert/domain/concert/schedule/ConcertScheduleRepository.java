package com.hanghae.concert.domain.concert.schedule;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Long> {

  List<ConcertSchedule> findAllByConcertId(Long concertId);
}