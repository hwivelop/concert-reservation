package com.hanghae.concert.application;

import com.hanghae.concert.domain.member.queue.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberQueueMyTurnServiceTest {

    @Autowired
    private MemberQueueService memberQueueService;

    @Autowired
    private MemberQueueCommandService memberQueueCommandService;

    @BeforeEach
    void setUp() {

        MemberQueue memberQueue = new MemberQueue(1L, 1L, 1L, "asdf1", TokenStatus.WAIT, null);
        MemberQueue memberQueue1 = new MemberQueue(2L, 2L, 1L, "asdf2", TokenStatus.WAIT, null);
        MemberQueue memberQueue2 = new MemberQueue(3L, 2L, 1L, "asdf2", TokenStatus.ACTIVE, null);
        MemberQueue memberQueue3 = new MemberQueue(4L, 2L, 1L, "asdf2", TokenStatus.ACTIVE, null);
        MemberQueue memberQueue4 = new MemberQueue(5L, 3L, 1L, "asdf3", TokenStatus.WAIT, null);

        memberQueueCommandService.save(memberQueue);
        memberQueueCommandService.save(memberQueue1);
        memberQueueCommandService.save(memberQueue2);
        memberQueueCommandService.save(memberQueue3);
        memberQueueCommandService.save(memberQueue4);
    }

    @Test
    @DisplayName("대기자 중에서 나의 순번 조회")
    void getMyTurn() {

        //given
        String token = "asdf3";

        //when
        Long myTurn = memberQueueService.getMyTurn(token);

        //then
        assertThat(myTurn).isEqualTo(3L);
    }
}