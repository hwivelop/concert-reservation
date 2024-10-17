package com.hanghae.concert.domain.member.queue;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberQueueCommandService {

    private final MemberQueueRepository memberQueueRepository;

    public MemberQueue save(MemberQueue memberQueue) {

        return memberQueueRepository.save(memberQueue);
    }
}
