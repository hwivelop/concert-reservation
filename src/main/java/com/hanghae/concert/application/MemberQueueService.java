package com.hanghae.concert.application;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.member.queue.dto.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberQueueService {

    private final MemberQueueQueryService memberQueueQueryService;
    private final MemberQueueCommandService memberQueueCommandService;
    private final ConcertQueryService concertQueryService;
    private final MemberQueryService memberQueryService;

    public MemberQueueDto createToken(Long memberId, Long concertId) {

        // 유저 검증
        if (!memberQueryService.existsMemberById(memberId)) {
            throw new MemberNotFoundException();
        }

        // 콘서트 검증
        ConcertDto concertDto = ConcertDto.of(concertQueryService.getConcertById(concertId));

        // 발급된 active 토큰이 있다면 그대로 반환
        return memberQueueQueryService.getMemberQueue(memberId, concertId)
                .map(MemberQueueDto::of)
                // 없으면 신규 토큰 발급
                .orElseGet(() -> {

                    // 대기자 수 확인하여 토큰 발급
                    TokenStatus tokenStatus = memberQueueQueryService.isActiveTokenOverCapacity(concertId, concertDto.capacity())
                            ? TokenStatus.WAIT : TokenStatus.ACTIVE;

                    // 토큰 상태에 따른 만료 일시
                    LocalDateTime expiredAt = tokenStatus == TokenStatus.ACTIVE ? LocalDateTime.now().plusMinutes(5) : null;

                    return MemberQueueDto.of(memberQueueCommandService.save(
                                    new MemberQueue(
                                            null,
                                            memberId,
                                            concertDto.concertId(),
                                            UUID.randomUUID().toString(),
                                            tokenStatus,
                                            expiredAt
                                    )
                            )
                    );
                });
    }

    public Long getMyTurn(String token) {

        MemberQueue memberQueue = memberQueueQueryService.getTokenStatus(token)
                .orElse(null);

        if (memberQueue.getTokenStatus() != TokenStatus.WAIT) {
            return null;
        }

        return memberQueueQueryService.getRankByToken(memberQueue.getId());
    }
}
