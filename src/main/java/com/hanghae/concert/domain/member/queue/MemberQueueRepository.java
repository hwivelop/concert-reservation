package com.hanghae.concert.domain.member.queue;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.time.*;
import java.util.*;

public interface MemberQueueRepository extends JpaRepository<MemberQueue, Long> {

    int countByConcertIdAndTokenStatus(Long concertId, TokenStatus tokenStatus);

    Optional<MemberQueue> findByMemberIdAndConcertIdAndTokenStatus(Long memberId, Long concertId, TokenStatus tokenStatus);

    boolean existsByMemberIdAndConcertIdAndTokenStatus(Long memberId, Long concertId, TokenStatus tokenStatus);

    Optional<TokenStatus> findTokenStatusByToken(String token);

    @Query("" +
            "SELECT COUNT(mq) " +
            "FROM MemberQueue mq " +
            "WHERE mq.createdAt < ( " +
            "  SELECT mq2.createdAt " +
            "  FROM MemberQueue mq2 " +
            "  WHERE mq2.token = :token " +
            ")"
    )
    Long findRankByToken(@Param("token") String token);

    void deleteByTokenStatusAndExpiredAtBefore(TokenStatus tokenStatus, LocalDateTime expiredAt);

    @Query("SELECT DISTINCT mq.concertId FROM MemberQueue mq WHERE mq.tokenStatus = :tokenStatus ")
    List<Long> findAllConcertIds(@Param("tokenStatus") TokenStatus tokenStatus);

    @Query("SELECT mq FROM MemberQueue mq " + "WHERE mq.tokenStatus = :tokenStatus " + "AND mq.concertId = :concertId " + "ORDER BY mq.createdAt ASC")
    List<MemberQueue> findChangeExpiredToActive(@Param("concertId") Long concertId, @Param("tokenStatus") TokenStatus tokenStatus, Pageable pageable);

    List<MemberQueue> findByConcertIdAndTokenStatus(Long concertId, TokenStatus tokenStatus);
}