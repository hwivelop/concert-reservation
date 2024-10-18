package com.hanghae.concert.application;

import com.hanghae.concert.domain.concert.seat.*;
import com.hanghae.concert.domain.concert.seat.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReservationSearchSeatsServiceTest {

    @Autowired
    private ConcertSeatRepository concertSeatRepository;
    @Autowired
    private ConcertSeatQueryService concertSeatQueryService;

    @Test
    @DisplayName("예약 불가능 좌석을 조회한다.")
    void getImpossibleSeats() {

        //given
        Long concertScheduleId = 1L;

        concertSeatRepository.saveAll(
                List.of(
                        new ConcertSeat(1L, concertScheduleId, 1, true),  // 예약된 좌석
                        new ConcertSeat(2L, concertScheduleId, 2, false)  // 예약되지 않은 좌석
                )
        );

        //when
        List<ConcertSeatDto> reservedSeats = concertSeatQueryService.getReservedSeats(concertScheduleId);

        //then
        assertThat(reservedSeats).hasSize(1);
        assertThat(reservedSeats)
                .extracting("seatNumber")
                .contains(1);  // 예약된 좌석만 포함
    }

    @Test
    @DisplayName("예약 가능 좌석을 조회하지 않는다.")
    void getPossibleSeats() {

        //given
        Long concertScheduleId = 1L;

        concertSeatRepository.saveAll(
                List.of(
                        new ConcertSeat(1L, concertScheduleId, 1, true),  // 예약된 좌석
                        new ConcertSeat(2L, concertScheduleId, 2, false)  // 예약되지 않은 좌석
                )
        );

        //when
        List<ConcertSeatDto> availableSeats = concertSeatQueryService.getReservedSeats(concertScheduleId);

        //then
        assertThat(availableSeats).hasSize(1);
        assertThat(availableSeats)
                .extracting("seatNumber")
                .doesNotContain(2);  // 예약되지 않은 좌석만 포함
    }
}