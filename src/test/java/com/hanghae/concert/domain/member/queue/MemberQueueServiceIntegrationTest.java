package com.hanghae.concert.domain.member.queue;

import com.hanghae.concert.application.*;
import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.dto.*;
import com.hanghae.concert.domain.member.dto.MemberDto;
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

        MemberDto memberDto = MemberDto.of(memberCommandService.initMember(
                new Member(1L, 0)
        ));
        memberId = memberDto.id();

        Concert concert = concertCommandService.save(
                new Concert(
                        null,
                        "콘서트 제목",
                        50,
                        150000,
                        ConcertStatus.AVAILABLE
                )
        );
        concertId = concert.getId();
    }

    @Transactional
    @DisplayName("대기열에 정원 이하의 Active 토큰만 존재한다면 Active 토큰이 발급된다.")
    @Test
    void getActiveToken() {

        // given
        Long memberId = 1L;

        // when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(new MemberQueueCreateDto(memberId, concertId));

        // then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.ACTIVE);
    }

    @Test
    @DisplayName("대기열에 정원 초과의 Active 토큰이 존재한다면 Wait 토큰이 발급된다.")
    void getWaitToken() {

        // given
        for (long memberId = 1L; memberId <= 50L; memberId++) {
            memberCommandService.initMember(new Member(memberId, 0));
            memberQueueService.createToken(new MemberQueueCreateDto(memberId, concertId));
        }
        Long waitMember = 51L;
        memberCommandService.initMember(new Member(waitMember, 0));

        // when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(new MemberQueueCreateDto(waitMember, concertId));

        // then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.WAIT);
    }
}