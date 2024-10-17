package com.hanghae.concert.batch;

import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.member.queue.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberQueueBatchService {

    private final MemberQueueCommandService memberQueueCommandService;
    private final MemberQueueQueryService memberQueueQueryService;
    private final ConcertQueryService concertQueryService;

    public void deleteExpiredToken() {

        memberQueueCommandService.deleteExpiredToken();
    }

    public void changeTokenStatusWaitToActive() {

        List<Long> concertIdsIncludeWait = memberQueueQueryService.getConcertIds(TokenStatus.WAIT);

        // 콘서트 별
        for (Long concertId : concertIdsIncludeWait) {
            int room = getCapacityRoom(concertId);

            if (room > 0) {
                List<MemberQueue> targets = memberQueueQueryService.getTokenStatusChangeTarget(
                        concertId, TokenStatus.WAIT, room
                );

                for (MemberQueue target : targets) {
                    target.changeStatus(TokenStatus.ACTIVE);
                    target.changeExpiredAt(LocalDateTime.now().plusMinutes(5));
                    memberQueueCommandService.save(target);
                }
            }
        }
    }

    private int getCapacityRoom(Long concertId) {

        Integer capacity = concertQueryService.getConcertById(concertId).getCapacity();

        int activeTokenCount = memberQueueQueryService.getActiveTokenCount(concertId);

        return capacity - activeTokenCount;
    }
}
