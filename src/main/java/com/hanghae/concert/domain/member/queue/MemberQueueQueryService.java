package com.hanghae.concert.domain.member.queue;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberQueueQueryService {

    private final MemberQueueRepository memberQueueRepository;


    public Integer getActiveTokenCount(Long concertId) {

       return memberQueueRepository.countByConcertIdAndTokenStatus(concertId, TokenStatus.ACTIVE);
    }

    public boolean isActiveTokenOverCapacity(Long concertId, int capacity) {

        long activeTokenCount = this.getActiveTokenCount(concertId);

        return activeTokenCount >= capacity;
    }

    public Optional<MemberQueue> getMemberQueue(Long memberId, Long concertId) {

        return memberQueueRepository.findByMemberIdAndConcertIdAndTokenStatus(memberId, concertId, TokenStatus.ACTIVE); //Todo wati
    }

    public boolean isAvailableToken(Long memberId, Long concertId) {

        return memberQueueRepository.existsByMemberIdAndConcertIdAndTokenStatus(memberId, concertId, TokenStatus.ACTIVE);
    }

    public Optional<MemberQueue> getTokenStatus(String token) {

        return memberQueueRepository.findTokenStatusByToken(token);
    }

    public Long getRankByToken(Long memberQueueId) {

        return memberQueueRepository.countByTokenStatusAndIdLessThanEqual(TokenStatus.WAIT, memberQueueId);
    }

    public List<Long> getConcertIds(TokenStatus tokenStatus) {

        return memberQueueRepository.findAllConcertIds(tokenStatus);
    }

    public List<MemberQueue> getTokenStatusChangeTarget(Long concertId, TokenStatus status, Pageable room) {

        return memberQueueRepository.findChangeExpiredToActive(concertId, status, room);
    }
}
