package com.hanghae.concert.batch;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.member.queue.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberQueueChangeStatusBatchServiceTest {

    @Autowired
    private MemberQueueBatchService memberQueueBatchService;

    @Autowired
    private MemberQueueRepository memberQueueRepository;

    @Autowired
    private MemberQueueCommandService memberQueueCommandService;

    @Autowired
    private ConcertCommandService concertCommandService;


    private static Long concertId;


    @BeforeEach
    void setUp() {
        Concert concert = new Concert(null, "제목", 50, 100000, ConcertStatus.AVAILABLE);
        concertId = concertCommandService.save(concert).getId();

        for (int i = 0; i < 10; i++) {
            MemberQueue memberQueue = new MemberQueue(
                    null,
                    1L,
                    concertId,
                    UUID.randomUUID().toString(),
                    TokenStatus.WAIT,
                    null
            );
            memberQueueRepository.save(memberQueue);
        }

        for (int j = 0; j < 40; j++) {
            MemberQueue memberQueue2 = new MemberQueue(
                    null,
                    1L,
                    concertId,
                    UUID.randomUUID().toString(),
                    TokenStatus.ACTIVE,
                    LocalDateTime.now().plusMinutes(5)
            );
            memberQueueRepository.save(memberQueue2);
        }
    }

    @Test
    @DisplayName("WAIT 상태의 토큰을 ACTIVE로 변경하고 만료 시간을 5분 뒤로 설정한다.")
    void changeTokenStatusWaitToActiveTest() {

        // when
        memberQueueBatchService.changeTokenStatusWaitToActive();

        // then
        List<MemberQueue> updatedQueues = memberQueueRepository.findByConcertIdAndTokenStatus(concertId, TokenStatus.ACTIVE);
        assertThat(updatedQueues).hasSize(50);  // 50개의 ACTIVE 토큰이 되어야 함

        // 만료 시간이 5분 뒤로 설정되었는지 확인
        for (MemberQueue queue : updatedQueues) {
            assertThat(queue.getExpiredAt()).isAfter(LocalDateTime.now().plusMinutes(4));
            assertThat(queue.getExpiredAt()).isBefore(LocalDateTime.now().plusMinutes(6));
        }
    }
}
