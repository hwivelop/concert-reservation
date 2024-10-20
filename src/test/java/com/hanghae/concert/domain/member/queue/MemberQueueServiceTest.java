package com.hanghae.concert.domain.member.queue;

import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.queue.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberQueueServiceTest {

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private ConcertQueryService concertQueryService;

    @Mock
    private MemberQueueCommandService memberQueueCommandService;

    @Mock
    private MemberQueueQueryService memberQueueQueryService;

    @InjectMocks
    private MemberQueueService memberQueueService;

    private Long memberId;
    private Long concertId;
    private Integer capacity;
    private Concert concert;
    private Member member;

    @BeforeEach
    void setUp() {

        memberId = 1L;
        concertId = 1L;
        capacity = 50;

        concert = new Concert(
                concertId,
                "콘서트 제목",
                capacity,
                150000,
                ConcertStatus.AVAILABLE
        );

        member = new Member(memberId, 0);

        when(concertQueryService.getConcertById(concertId)).thenReturn(concert);
        when(memberQueryService.existsMemberById(memberId)).thenReturn(true);
    }

    @Test
    @DisplayName("대기열에 정원 이하의 Active 토큰만 존재한다면 Active 토큰이 발급된다.")
    void getActiveToken() {

        // given
        MemberQueue mockActiveMemberQueue = createMockMemberQueue(TokenStatus.ACTIVE, LocalDateTime.now().plusMinutes(5));

        when(memberQueueQueryService.isActiveTokenOverCapacity(concertId, capacity)).thenReturn(false);
        when(memberQueueCommandService.save(any(MemberQueue.class))).thenReturn(mockActiveMemberQueue);

        // when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(memberId, concertId);

        // then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.ACTIVE);
    }

    @Test
    @DisplayName("대기열에 정원 초과의 Active 토큰이 존재한다면 WAIT 토큰이 발급된다.")
    void getWaitToken() {

        // given
        MemberQueue mockWaitMemberQueue = createMockMemberQueue(TokenStatus.WAIT, null);

        when(memberQueueQueryService.isActiveTokenOverCapacity(concertId, capacity)).thenReturn(true);
        when(memberQueueCommandService.save(any(MemberQueue.class))).thenReturn(mockWaitMemberQueue);

        // when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(memberId, concertId);

        // then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.WAIT);
    }

    private MemberQueue createMockMemberQueue(TokenStatus tokenStatus, LocalDateTime expiredAt) {
        return new MemberQueue(
                1L,
                memberId,
                concertId,
                UUID.randomUUID().toString(),
                tokenStatus,
                expiredAt
        );
    }
}
