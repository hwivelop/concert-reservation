package com.hanghae.concert.batch.task;

import com.hanghae.concert.batch.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberQueueTask {

    private final MemberQueueBatchService memberQueueBatchService;

    @Scheduled(cron = "0 * * * * *")
    public void deleteExpiredToken() {

        memberQueueBatchService.deleteExpiredToken();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void changeTokenStatusWaitToActive() {

        memberQueueBatchService.changeTokenStatusWaitToActive();
    }
}
