package com.hanghae.concert.domain.member.queue;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberQueueQueryService {

    private final MemberQueueRepository memberQueueRepository;


    public boolean isActiveTokenOverCapacity(Long concertId, int capacity) {

        long activeTokenCount = memberQueueRepository.countByConcertIdAndTokenStatus(concertId, TokenStatus.ACTIVE);

        return activeTokenCount >= capacity;
    }

    public Optional<MemberQueue> getMemberQueue(Long memberId, Long concertId) {

        return memberQueueRepository.findByMemberIdAndConcertIdAndTokenStatus(memberId, concertId, TokenStatus.ACTIVE);
    }

    public boolean isAvailableToken(Long memberId, Long concertId) {

        return memberQueueRepository.existsByMemberIdAndConcertIdAndTokenStatus(memberId, concertId, TokenStatus.ACTIVE);
    }

    public Optional<TokenStatus> getTokenStatus(String token) {

        return memberQueueRepository.findTokenStatusByToken(token);
    }

    public Long getRankByToken(String token) {

        return memberQueueRepository.findRankByToken(token);
    }
}
