package com.hanghae.concert.application;

import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.schedule.*;
import com.hanghae.concert.domain.concert.schedule.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.reservation.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReservationSearchDatesServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MemberCommandService memberCommandService;
    @Autowired
    private MemberQueueCommandService memberQueueCommandService;
    @Autowired
    private ConcertCommandService concertCommandService;
    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        // 테스트를 위한 초기 데이터 설정
//        reservationRepository.deleteAll();  // 기존 데이터를 삭제하여 충돌을 방지
        }

    @Test
    @Transactional
    @DisplayName("예약 가능한 날짜를 검색한다.")
    void searchAvailableDates() {
        Member member = memberRepository.save(new Member(1L, 0));
        memberQueueCommandService.save(new MemberQueue(1L, 1L, 1L, "asdf", TokenStatus.ACTIVE, LocalDateTime.now().plusMinutes(5)));
        Concert concert = concertCommandService.save(new Concert(1L, "제목", 50, 150000, ConcertStatus.AVAILABLE));
        concertScheduleRepository.save(new ConcertSchedule(1L, 1L, 10, LocalDate.now().plusDays(1).atStartOfDay()));
        concertScheduleRepository.save(new ConcertSchedule(1L, 1L, 0, LocalDate.now().plusDays(2).atStartOfDay()));
        concertScheduleRepository.save(new ConcertSchedule(1L, 1L, 8, LocalDate.now().plusDays(3).atStartOfDay()));


        Long memberId = member.getId();
        Long concertId = concert.getId();

        // when
        List<ConcertScheduleDto> concertScheduleDtos = reservationService.searchAvailableDates(memberId, concertId);

        // then
        assertThat(concertScheduleDtos)
                .extracting("startAt")
                .doesNotContain(LocalDate.now().plusDays(2).atStartOfDay());
    }
}