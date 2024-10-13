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


    public Boolean isActiveTokenOverCapacity(Integer capacity) {

        return memberQueueRepository.isActiveTokenGoeCapacity(capacity, TokenStatus.ACTIVE);
    }

    public Optional<MemberQueue> getMemberQueue(Long memberId, Long concertId) {

        return memberQueueRepository.findByMemberIdAndConcertIdAndTokenStatus(memberId, concertId, TokenStatus.ACTIVE);
    }
}
