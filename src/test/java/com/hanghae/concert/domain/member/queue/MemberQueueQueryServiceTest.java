package com.hanghae.concert.domain.member.queue;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberQueueQueryServiceTest {

    @Mock
    private MemberQueueRepository memberQueueRepository;

    @InjectMocks
    private MemberQueueQueryService memberQueueQueryService;

    //todo review 이게 의미 있는 테스트인가..

    @Test
    @DisplayName("대기열에 정원 이하의 Active 토큰이 존재한다면 True 를 반환한다.")
    void loeCapacityThenTrue() {

        //given
        Long concertId = 1L;
        Integer capacity = 50;
        Integer cnt = 51;

        when(memberQueueRepository.countByConcertIdAndTokenStatus(concertId, TokenStatus.ACTIVE)).thenReturn(cnt);

        //when
        Boolean overCapacity = memberQueueQueryService.isActiveTokenOverCapacity(concertId, capacity);

        //then
        assertThat(overCapacity).isTrue();
    }

    @Test
    @DisplayName("대기열에 정원 이하의 Active 토큰이 존재한다면 False 를 반환한다.")
    void loeCapacityThenFalse() {

        //given
        Long concertId = 1L;
        Integer capacity = 50;
        Integer cnt = 49;

        when(memberQueueRepository.countByConcertIdAndTokenStatus(concertId, TokenStatus.ACTIVE)).thenReturn(cnt);

        //when
        Boolean overCapacity = memberQueueQueryService.isActiveTokenOverCapacity(concertId, capacity);

        //then
        assertThat(overCapacity).isFalse();
    }
}