package com.hanghae.concert.domain.concert.seat;

import com.hanghae.concert.domain.concert.seat.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertSeatQueryServiceTest {

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private ConcertSeatQueryService concertSeatQueryService;

    @Test
    @DisplayName("예약된 좌석을 조회하면, 상태가 true인 좌석만 조회된다.")
    void getReservedSeats() {

        //given
        Long concertScheduleId = 1L;

        when(concertSeatRepository.findAllByConcertScheduleId(concertScheduleId))
                .thenReturn(
                        List.of(
                                new ConcertSeat(1L, concertScheduleId, 1, true),
                                new ConcertSeat(1L, concertScheduleId, 2, false),
                                new ConcertSeat(1L, concertScheduleId, 3, true)
                        )
                );

        //when
        List<ConcertSeatDto> reservedSeats = concertSeatQueryService.getReservedSeats(concertScheduleId);

        // then
        assertThat(reservedSeats)
                .hasSize(2)  // reservedSeats의 크기가 2인지 확인
                .extracting("seatNumber")  // seatNumber 필드를 추출
                .containsExactlyInAnyOrder(1, 3);  // 추출된 seatNumber 값들이 1과 3인지 확인
    }

    @Test
    @DisplayName("예약된 좌석을 조회하면, 상태가 false 인 좌석은 조회되지 않는다.")
    void getNotReservedSeats() {

        //given
        Long concertScheduleId = 1L;

        when(concertSeatRepository.findAllByConcertScheduleId(concertScheduleId))
                .thenReturn(
                        List.of(
                                new ConcertSeat(1L, concertScheduleId, 1, true),
                                new ConcertSeat(1L, concertScheduleId, 2, false),
                                new ConcertSeat(1L, concertScheduleId, 3, true)
                        )
                );

        //when
        List<ConcertSeatDto> reservedSeats = concertSeatQueryService.getReservedSeats(concertScheduleId);

        // then
        assertThat(reservedSeats)
                .extracting("seatNumber")
                .doesNotContain(2);
    }
}