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
        Integer capacity = 50;

        when(memberQueueRepository.isActiveTokenGoeCapacity(capacity, TokenStatus.ACTIVE)).thenReturn(true);

        //when
        Boolean overCapacity = memberQueueQueryService.isActiveTokenOverCapacity(capacity);

        //then
        assertThat(overCapacity).isTrue();
    }

    @Test
    @DisplayName("대기열에 정원 이하의 Active 토큰이 존재한다면 False 를 반환한다.")
    void loeCapacityThenFalse() {

        //given
        Integer capacity = 50;

        when(memberQueueRepository.isActiveTokenGoeCapacity(capacity, TokenStatus.ACTIVE)).thenReturn(false);

        //when
        Boolean overCapacity = memberQueueQueryService.isActiveTokenOverCapacity(capacity);

        //then
        assertThat(overCapacity).isFalse();
    }
}