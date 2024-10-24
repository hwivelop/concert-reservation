package com.hanghae.concert.application;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.exception.*;
import com.hanghae.concert.domain.concert.seat.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.member.queue.*;
import com.hanghae.concert.domain.member.queue.exception.*;
import com.hanghae.concert.domain.payment.*;
import com.hanghae.concert.domain.reservation.*;
import com.hanghae.concert.domain.reservation.exception.*;
import lombok.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberQueueQueryService memberQueueQueryService;
    private final MemberQueueCommandService memberQueueCommandService;
    private final ConcertQueryService concertQueryService;
    private final ReservationQueryService reservationQueryService;
    private final PaymentHistoryCommandService paymentHistoryCommandService;
    private final ConcertSeatQueryService concertSeatQueryService;

    public ReservationPayDto pay(PaymentHistoryCreateDto dto) {

        // 검증
        Long memberId = dto.memberId();
        Long concertId = dto.concertId();
        Long reservationId = dto.reservationId();

        validateMemberAndConcert(memberId, concertId);

        // 예약 조회
        Reservation reservation = reservationQueryService.getReservationById(reservationId);

        // 만료 여부 검증
        if (reservation.isExpired()) {
            throw new ExpiredReservationException();
        }

        // 잔액 차감
        Integer reservationPrice = reservation.getReservationPrice();

        memberCommandService.updateBalance(new MemberUpdateBalanceDto(memberId, reservationPrice, PaymentType.USE));

        // 결제내역 저장
        paymentHistoryCommandService.savePaymentHistory(
                new PaymentHistory(
                        null,
                        memberId,
                        reservationId,
                        PaymentType.USE,
                        reservationPrice
                )
        );

        // 예약상태 reservation 으로 변경
        reservation.changeStatus(ReservationStatus.RESERVED);

        ConcertSeat concertSeat = concertSeatQueryService.getConcertSeatById(reservation.getConcertSeatId());
        concertSeat.changeReserved(true);

        // 대기열 변경
        MemberQueue memberQueue = memberQueueQueryService.getMemberQueue(memberId, concertId)
                .orElseThrow(MemberQueueNotFoundException::new);

        memberQueue.changeStatus(TokenStatus.DONE);
        memberQueueCommandService.save(memberQueue);

        return new ReservationPayDto(
                concertId, reservation.getReservationStatus(), memberQueue.getTokenStatus(), reservation.getReservationPrice()
        );
    }

    private void validateMemberAndConcert(Long memberId, Long concertId) {

        // 유저 검증
        if (!memberQueryService.existsMemberById(memberId)) {
            throw new MemberNotFoundException();
        }

        // 토큰 검증
        if (!memberQueueQueryService.isAvailableToken(memberId, concertId)) {
            throw new ActiveTokenNotFoundException();
        }

        // 콘서트 검증
        if (!concertQueryService.existsById(concertId)) {
            throw new ConcertNotFoundException();
        }
    }
}
