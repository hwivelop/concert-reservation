package com.hanghae.concert.application;

import com.hanghae.concert.api.concert.dto.request.*;
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

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReservationServiceIntegrationTest {

    @Autowired
    private MemberQueueRepository memberQueueRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    @DisplayName("동시에 임시 좌석 예약을 할 수 있나?")
    void temp_reservation() throws InterruptedException {
        //given

        memberRepository.save(new Member(null, 10000000));
        Concert concert = concertRepository.save(new Concert(null, "콘서트명", 50, 150000, ConcertStatus.AVAILABLE));
        concertScheduleRepository.save(new ConcertSchedule(null, concert.getId(), 10, LocalDate.now().plusDays(1).atStartOfDay()));
        memberQueueRepository.save(new MemberQueue(1L, 1L, 1L, "asdf", TokenStatus.ACTIVE, LocalDateTime.now().plusMinutes(5)));

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        ReservationSeatRequest request = new ReservationSeatRequest(1L, 1L, 1L, 1);

        //when
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.tempReservation(request);
                } finally {
                    latch.countDown(); // 스레드가 종료되면 카운트 감소
                }
            });
        }

        latch.await();

        //then
        long seatCnt = concertSeatRepository.count();
        assertThat(seatCnt).isEqualTo(1);

        long reservationCnt = reservationRepository.count();
        assertThat(reservationCnt).isEqualTo(1);
    }
}