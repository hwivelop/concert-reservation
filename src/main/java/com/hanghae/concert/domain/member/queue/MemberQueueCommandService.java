package com.hanghae.concert.domain.member.queue;

import com.hanghae.concert.domain.member.queue.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberQueueCommandService {

    private final MemberQueueRepository memberQueueRepository;

    public MemberQueueDto saveMemberQueue(
            Long memberId,
            Long concertId,
            TokenStatus tokenStatus,
            LocalDateTime expiredAt
    ) {

        MemberQueue memberQueue = memberQueueRepository.save(
                new MemberQueue(
                        null, //todo review id를 null 로 하는게 맞는지, id 없는 생성자를 만드는게 맞는지
                        memberId,
                        concertId,
                        UUID.randomUUID().toString(),
                        tokenStatus,
                        expiredAt
                )
        );

        return MemberQueueDto.of(memberQueue);
    }
}
