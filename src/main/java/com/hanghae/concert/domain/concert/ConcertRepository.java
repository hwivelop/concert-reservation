package com.hanghae.concert.domain.concert;

import org.springframework.data.jpa.repository.*;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    boolean existsById(Long concertId);
}