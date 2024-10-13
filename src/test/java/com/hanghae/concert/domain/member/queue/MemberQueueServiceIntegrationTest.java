package com.hanghae.concert.domain.member.queue;

import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.dto.*;
import com.hanghae.concert.domain.member.queue.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import static org.assertj.core.api.Assertions.*;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스를 클래스당 1개로 유지
@SpringBootTest
class MemberQueueServiceIntegrationTest {

    @Autowired
    private MemberCommandService memberCommandService;

    @Autowired
    private ConcertCommandService concertCommandService;

    @Autowired
    private MemberQueueService memberQueueService;

    private Long memberId;

    private Long concertId;

    @BeforeAll
    void setUp() {

        MemberDto memberDto = memberCommandService.saveMember();
        memberId = memberDto.id();

        ConcertDto concertDto = concertCommandService.saveConcert(
                new SaveConcertDto(
                        "콘서트 제목",
                        50,
                        150000,
                        ConcertStatus.AVAILABLE
                )
        );
        concertId = concertDto.concertId();  // 저장된 콘서트의 ID 사용
    }

    @Transactional
    @DisplayName("대기열에 정원 이하의 Active 토큰만 존재한다면 Active 토큰이 발급된다.")
    @Test
    void getActiveToken() {

        // given
        Long memberId = 1L;

        // when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(memberId, concertId);

        // then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.ACTIVE);
    }

    @Transactional
    @DisplayName("대기열에 정원 초과의 Active 토큰이 존재한다면 Wait 토큰이 발급된다.")
    @Test
    void getWaitToken() {

        // given
        Long memberId = 1L;

        for (long no = 1L; no <= 50L; no++) {
            memberQueueService.createToken(memberId, concertId);
        }

        // when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(memberId, concertId);

        // then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.WAIT);
    }
}