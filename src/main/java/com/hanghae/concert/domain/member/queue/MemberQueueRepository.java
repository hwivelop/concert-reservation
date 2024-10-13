package com.hanghae.concert.domain.member.queue;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface MemberQueueRepository extends JpaRepository<MemberQueue, Long> {

    @Query("SELECT COUNT(mq) >= :capacity FROM MemberQueue mq WHERE mq.tokenStatus = :tokenStatus")
    Boolean isActiveTokenGoeCapacity(
            @Param("capacity") long capacity,
            @Param("tokenStatus") TokenStatus tokenStatus
    );

    Optional<MemberQueue> findByMemberIdAndConcertIdAndTokenStatus(Long memberId, Long concertId, TokenStatus tokenStatus);
}