package com.hanghae.concert.domain.concert.schedule;

import com.hanghae.concert.domain.concert.schedule.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertScheduleQueryServiceTest {

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @InjectMocks
    private ConcertScheduleQueryService concertScheduleQueryService;

    @Test
    @DisplayName("예약 가능한 날짜가 있으면 가능한 콘서트 정보를 반환한다.")
    void getPossibleDates() {

        //given
        Long concertId = 1L;
        LocalDateTime startAt = LocalDateTime.now().plusDays(10);

        when(concertScheduleRepository.findAllByConcertId(concertId))
                .thenReturn(
                        List.of(new ConcertSchedule(1L, concertId, 50, startAt))
                );

        //when
        List<ConcertScheduleDto> availableConcertSchedules = concertScheduleQueryService.getAvailableConcertSchedules(concertId);

        //then
        assertThat(availableConcertSchedules).hasSize(1)
                .extracting("concertId", "startAt")
                .containsExactlyInAnyOrder(
                        tuple(concertId, startAt)
                );
    }

    @Test
    @DisplayName("예약 가능한 날짜가 없으면 빈 리스트를 반환한다.")
    void getImpossibleDates() {

        //given
        Long concertId = 1L;
        LocalDateTime startAt = LocalDateTime.now().plusDays(10);

        when(concertScheduleRepository.findAllByConcertId(concertId))
                .thenReturn(List.of());

        //when
        List<ConcertScheduleDto> availableConcertSchedules = concertScheduleQueryService.getAvailableConcertSchedules(concertId);

        //then
        assertThat(availableConcertSchedules).hasSize(0);
    }
}