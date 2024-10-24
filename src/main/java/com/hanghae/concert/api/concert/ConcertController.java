package com.hanghae.concert.api.concert;

import com.hanghae.concert.api.common.response.*;
import com.hanghae.concert.api.concert.dto.request.*;
import com.hanghae.concert.api.concert.dto.response.*;
import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.concert.schedule.dto.*;
import com.hanghae.concert.domain.concert.seat.dto.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController implements IConcertController {

    private final ReservationService reservationService;
    private final ConcertService concertService;
    private final PaymentService paymentService;

    @PostMapping
    public BasicResponse<Void> createConcert(
            @RequestBody CreateConcertRequest request
    ) {

        concertService.createConcert(request.toRequest());

        return BasicResponse.ok();
    }

    /**
     * 예약 가능 날짜 조회
     */
    @Override
    @GetMapping("/{concertId}/schedules")
    public BasicResponse<List<ConcertAvailableDateResponse>> getConcertSchedules(
            @PathVariable Long concertId,
            @RequestParam Long memberId
    ) {

        List<ConcertScheduleDto> concertScheduleDtos = reservationService.searchAvailableDates(memberId, concertId);

        return BasicResponse.ok(
                ConcertAvailableDateResponse.toResponse(concertScheduleDtos)
        );
    }

    /**
     * 예약이 완료된 조회
     */
    @Override
    @GetMapping("{concertId}/schedule/{concertScheduleId}/seats")
    public BasicResponse<List<ConcertAvailableSeatResponse>> getConcertSeats(
            @RequestParam Long memberId,
            @PathVariable Long concertId,
            @PathVariable Long concertScheduleId
    ) {

        List<ConcertSeatDto> concertSeatDtos = reservationService.searchReservedSeats(memberId, concertId, concertScheduleId);

        return BasicResponse.ok(
                ConcertAvailableSeatResponse.toResponse(concertId, concertSeatDtos)
        );
    }

    /**
     * 좌석 예약
     */
    @Override
    @PostMapping("/reservation")
    public BasicResponse<Void> reserveConcert(
            @RequestBody ReservationSeatRequest request
    ) {

        reservationService.tempReservation(request);

        return BasicResponse.ok();
    }

    /**
     * 콘서트 좌석 결제
     */
    @Override
    @PostMapping("/pay")
    public BasicResponse<ReservationPayResponse> payConcert(
            @RequestBody @Valid PayRequest request
    ) {

        ReservationPayDto payDto = paymentService.pay(request.toRequest());

        return BasicResponse.ok(
                ReservationPayResponse.toResponse(payDto)
        );
    }
}
