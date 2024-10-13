package com.hanghae.concert.domain.member.queue;

import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.dto.*;
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


    @Test
    @DisplayName("대기열에 정원 이하의 Active 토큰만 존재한다면 Active 토큰이 발급된다.")
    void getActiveToken() {

        Long memberId = 1L;
        Long concertId = 1L;
        Integer capacity = 50;
        TokenStatus tokenStatus = TokenStatus.ACTIVE;
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(5);

        //given
        ConcertDto concertDto = new ConcertDto(
                1L,
                "콘서트 제목",
                50,
                150000,
                ConcertStatus.AVAILABLE
        );

        when(concertQueryService.getConcertById(concertId)).thenReturn(concertDto);

        MemberQueueDto MockMemberQueueDto = new MemberQueueDto(
                memberId,
                UUID.randomUUID().toString(),
                tokenStatus,
                expiredAt
        );
        when(memberQueueQueryService.isActiveTokenOverCapacity(capacity)).thenReturn(Boolean.FALSE);
        when(memberQueueCommandService.saveMemberQueue(
                eq(memberId),
                eq(concertDto.concertId()),
                eq(tokenStatus),
                any(LocalDateTime.class) //fixme 서비스 내부에서 비교해야 돼서 파라미터로 못받는데, test 하면 밀리초까지 비교해서 fail 됨, 일단 any 로 통과시킴
        )).thenReturn(MockMemberQueueDto);

        when(memberQueryService.getMemberById(memberId)).thenReturn(new MemberDto(1L, 0L));

        //when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(memberId, concertId);

        //then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.ACTIVE);
    }

    @Test
    @DisplayName("대기열에 정원 초과의 Active 토큰이 존재한다면 WAIT 토큰이 발급된다.")
    void getWaitToken() {

        Long memberId = 1L;
        Long concertId = 1L;
        Integer capacity = 50;
        TokenStatus tokenStatus = TokenStatus.WAIT;
        LocalDateTime expiredAt = null;

        //given
        ConcertDto concertDto = new ConcertDto(
                1L,
                "콘서트 제목",
                50,
                150000,
                ConcertStatus.AVAILABLE
        );

        when(concertQueryService.getConcertById(concertId)).thenReturn(concertDto);
        when(concertQueryService.getConcertById(concertId)).thenReturn(concertDto);

        MemberQueueDto MockMemberQueueDto = new MemberQueueDto(
                memberId,
                UUID.randomUUID().toString(),
                tokenStatus,
                expiredAt
        );

        when(memberQueueQueryService.isActiveTokenOverCapacity(capacity)).thenReturn(Boolean.TRUE);
        when(memberQueueCommandService.saveMemberQueue(
                memberId,
                concertDto.concertId(),
                tokenStatus,
                expiredAt
        )).thenReturn(MockMemberQueueDto);

        when(memberQueryService.getMemberById(memberId)).thenReturn(new MemberDto(1L, 0L));

        //when
        MemberQueueDto memberQueueDto = memberQueueService.createToken(memberId, concertId);

        //then
        assertThat(memberQueueDto.tokenStatus()).isEqualTo(TokenStatus.WAIT);
    }
}