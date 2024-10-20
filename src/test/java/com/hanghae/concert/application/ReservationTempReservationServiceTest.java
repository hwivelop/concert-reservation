package com.hanghae.concert.application;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.schedule.*;
import com.hanghae.concert.domain.concert.seat.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.reservation.*;
import com.hanghae.concert.domain.reservation.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.annotation.*;
import org.springframework.test.context.jdbc.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReservationTempReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;
    @Autowired
    private ConcertSeatQueryService concertSeatQueryService;
    @Autowired
    private ConcertQueryService concertQueryService;
    @Autowired
    private MemberQueueRepository memberQueueRepository;


    Long memberId;
    Long memberQueueId;
    Long concertId;
    Long concertScheduleId;

    @BeforeEach
    void setUp() {


        Member member = memberRepository.save(new Member(null, 0));
        memberId = member.getId();

        Concert concert =concertRepository.save(new Concert(1L, "제목", 50, 10000, ConcertStatus.AVAILABLE));
        concertId = concert.getId();

        MemberQueue memberQueue = memberQueueRepository.save(new MemberQueue(null, memberId, concertId, "asdf", TokenStatus.ACTIVE, LocalDateTime.now().plusMinutes(5)));
        memberQueueId = memberQueue.getId();

        ConcertSchedule concertSchedule = concertScheduleRepository.save(new ConcertSchedule(1L, concertId, 10, LocalDateTime.now().plusDays(1)));
        concertScheduleId = concertSchedule.getId();
    }

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
        concertRepository.deleteAll();
        memberQueueRepository.deleteAll();
    }

    @Test
    @DirtiesContext
    @DisplayName("임시 좌석으로 예약을 한다.")
    void tempReservation() {

        // given
        int seatNumber = 1;

        // when
        ReservationDto reservationDto = reservationService.tempReservation(memberId, concertId, concertScheduleId, seatNumber);

        // then
        assertThat(reservationDto).isNotNull();
        assertThat(reservationDto.concertSeatId()).isNotNull();
        assertThat(reservationDto.reservationStatus()).isEqualTo(ReservationStatus.TEMP_RESERVED);

        // 잔여 좌석이 감소했는지 검증
        ConcertSchedule updatedSchedule = concertScheduleRepository.findById(concertScheduleId).get();
        assertThat(updatedSchedule.getRemainingSeat()).isEqualTo(9);

        // 예약된 좌석이 존재하는지 확인
        Optional<ConcertSeat> concertSeat = concertSeatQueryService.getConcertSeat(concertScheduleId, seatNumber);
        assertThat(concertSeat).isPresent();
        assertThat(concertSeat.get().getSeatNumber()).isEqualTo(seatNumber);
    }

    @Test
    @DirtiesContext
    @DisplayName("잔여 좌석이 0이 되면 콘서트가 SOLD_OUT 상태로 변경된다.")
    void tempReservationSoldOutTest() {

        // given
        for (int i = 1; i <= 10; i++) {
            reservationService.tempReservation(memberId, concertId, concertScheduleId, i);
        }

        // when
        reservationService.tempReservation(memberId, concertId, concertScheduleId, 11);

        // then
        Concert updatedConcert = concertQueryService.getConcertById(concertId);
        assertThat(updatedConcert.getConcertStatus()).isEqualTo(ConcertStatus.SOLD_OUT);
    }
}