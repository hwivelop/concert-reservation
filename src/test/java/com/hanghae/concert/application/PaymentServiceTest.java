package com.hanghae.concert.application;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.payment.*;
import com.hanghae.concert.domain.reservation.*;
import com.hanghae.concert.domain.reservation.exception.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.annotation.*;

import java.time.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ReservationCommandService reservationCommandService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MemberQueueRepository memberQueueRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    Long memberId;
    Long concertId;
    Long reservationId;

    @BeforeEach
    void setUp() {

        Member member = new Member(1L, 50000);
        memberRepository.save(member);

        Concert concert = new Concert(1L, "제목", 50, 10000, ConcertStatus.AVAILABLE);
        concertRepository.save(concert);

        MemberQueue memberQueue = new MemberQueue(1L, member.getId(), concert.getId(), "asdf", TokenStatus.ACTIVE, LocalDateTime.now().plusMinutes(5));
        memberQueueRepository.save(memberQueue);

        Reservation reservation = new Reservation(1L, member.getId(), 1L, ReservationStatus.TEMP_RESERVED, 10000);  // 예약 금액 10000
        reservationRepository.save(reservation);

        memberId = member.getId();
        concertId = concert.getId();
        reservationId = reservation.getId();
    }

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
        concertRepository.deleteAll();
        memberQueueRepository.deleteAll();
        reservationRepository.deleteAll();
        paymentHistoryRepository.deleteAll();
    }

    @Test
    @DirtiesContext
    @DisplayName("결제가 완료 후 status Done 으로 변경 된다.")
    void paySuccessTest() {

        // given

        // when
        paymentService.pay(new PaymentHistoryCreateDto(memberId, concertId, reservationId));

        // then
        // 잔액 차감 확인
        Member updatedMember = memberRepository.findById(memberId).orElseThrow();
        assertThat(updatedMember.getBalance()).isEqualTo(40000);  // 50000 - 10000 = 40000

        // 예약 상태 변경 확인
        Reservation updatedReservation = reservationRepository.findById(reservationId).orElseThrow();
        assertThat(updatedReservation.getReservationStatus()).isEqualTo(ReservationStatus.RESERVED);

        // 대기열 상태 변경 확인
        MemberQueue updatedQueue = memberQueueRepository.findByMemberIdAndConcertId(memberId, concertId).orElseThrow();
        assertThat(updatedQueue.getTokenStatus()).isEqualTo(TokenStatus.DONE);

        // 결제 내역 저장 확인
        assertThat(paymentHistoryRepository.findAll()).hasSize(1);
        PaymentHistory paymentHistory = paymentHistoryRepository.findAll().get(0);
        assertThat(paymentHistory.getMemberId()).isEqualTo(memberId);
        assertThat(paymentHistory.getReservationId()).isEqualTo(reservationId);
        assertThat(paymentHistory.getPaymentType()).isEqualTo(PaymentType.USE);
        assertThat(paymentHistory.getAmount()).isEqualTo(10000);
    }

    @Test
    @DirtiesContext
    @DisplayName("만료된 예약에 대해 결제를 시도할 경우 예외가 발생한다.")
    void payExpiredReservationTest() {

        // given

        // 먼저 저장된 예약을 가져옴
        Reservation reservation = reservationRepository.findById(reservationId).get();
        Long reservationId = reservation.getId();

        // 만료된 예약 상태로 변경
        reservation.changeStatus(ReservationStatus.EXPIRED);
        reservationCommandService.save(reservation);

        // when, then
        ExpiredReservationException exception = assertThrows(ExpiredReservationException.class, () -> {
            paymentService.pay(new PaymentHistoryCreateDto(memberId, concertId, reservationId));
        });

        // then
        assertEquals("만료된 예약 입니다.", exception.getMessage());
    }
}