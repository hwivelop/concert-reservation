package com.hanghae.concert.batch;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.member.queue.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberQueueBatchService {

    private final MemberQueueCommandService memberQueueCommandService;
    private final MemberQueueQueryService memberQueueQueryService;
    private final ConcertQueryService concertQueryService;

    public void deleteExpiredToken() {

        memberQueueCommandService.deleteExpiredToken();
    }

    @Transactional
    public void changeTokenStatusWaitToActive() {

        List<Long> concertIdsIncludeWait = memberQueueQueryService.getConcertIds(TokenStatus.WAIT);

        // 콘서트 별
        for (Long concertId : concertIdsIncludeWait) {
            int room = getCapacityRoom(concertId);

            if (room > 0) {
                Pageable pageable = PageRequest.of(0, room);
                List<MemberQueue> targets = memberQueueQueryService.getTokenStatusChangeTarget(
                        concertId, TokenStatus.WAIT, pageable
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
