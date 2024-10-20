package com.hanghae.concert.batch;

import com.hanghae.concert.domain.member.queue.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class MemberQueueBatchServiceTest {


    @Autowired
    private MemberQueueBatchService memberQueueBatchService;

    @Autowired
    private MemberQueueRepository memberQueueRepository;


    @Test
    @DisplayName("만료된 토큰을 삭제한다.")
    void deleteExpiredToken() {

        //given
        Long memberId = 1L;
        Long concertId = 1L;
        TokenStatus tokenStatus = TokenStatus.ACTIVE;
        LocalDateTime expiredAt = LocalDateTime.now().minusMinutes(6);  // 만료된 날짜로 설정

        MemberQueue memberQueue = new MemberQueue(
                null,
                memberId,
                concertId,
                UUID.randomUUID().toString(),
                tokenStatus,
                expiredAt
        );

        // 토큰 저장
        memberQueueRepository.save(memberQueue);

        Optional<MemberQueue> savedMemberQueue = memberQueueRepository.findById(memberQueue.getId());
        assertThat(savedMemberQueue).isPresent();

        //when
        memberQueueBatchService.deleteExpiredToken();

        //then
        Optional<MemberQueue> deletedMemberQueue = memberQueueRepository.findById(memberQueue.getId());
        assertThat(deletedMemberQueue).isNotPresent();
    }

    @Test
    @DisplayName("만료되지 않은 토큰은 삭제되지 않는다.")
    void tokenNotDeletedIfNotExpired() {

        //given
        Long memberId = 1L;
        Long concertId = 1L;
        TokenStatus tokenStatus = TokenStatus.ACTIVE;
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(4);

        MemberQueue memberQueue = new MemberQueue(
                null,
                memberId,
                concertId,
                UUID.randomUUID().toString(),
                tokenStatus,
                expiredAt
        );

        memberQueueRepository.save(memberQueue);

        Optional<MemberQueue> savedMemberQueue = memberQueueRepository.findById(memberQueue.getId());
        assertThat(savedMemberQueue).isPresent();

        //when
        memberQueueBatchService.deleteExpiredToken();

        //then
        Optional<MemberQueue> notDeletedMemberQueue = memberQueueRepository.findById(memberQueue.getId());
        assertThat(notDeletedMemberQueue).isPresent();
    }
}