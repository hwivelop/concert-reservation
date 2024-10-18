package com.hanghae.concert.api.concert;

import com.hanghae.concert.api.concert.dto.request.*;
import com.hanghae.concert.api.concert.dto.response.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController implements IConcertController {

    /**
     * 예약 가능 날짜 조회
     */
    @Override
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<List<ConcertAvailableDateResponse>> getConcertSchedules(
            @RequestHeader String token
    ) {
        List<ConcertAvailableDateResponse> responses = List.of(
                new ConcertAvailableDateResponse(1L, LocalDateTime.now()),
                new ConcertAvailableDateResponse(2L, LocalDateTime.now()),
                new ConcertAvailableDateResponse(3L, LocalDateTime.now())
        );

        return ResponseEntity.ok(responses);
    }

    /**
     * 예약 가능 좌석 조회
     */
    @Override
    @GetMapping("/schedule/{concertScheduleId}/seats")
    public ResponseEntity<List<ConcertAvailableSeatResponse>> getConcertSeats(
            @RequestHeader String token,
            @PathVariable Long concertScheduleId
    ) {
        List<ConcertAvailableSeatResponse> responses = List.of(
                new ConcertAvailableSeatResponse(1L, 1L, 1),
                new ConcertAvailableSeatResponse(2L, 2L, 2),
                new ConcertAvailableSeatResponse(3L, 3L, 3)
        );

        return ResponseEntity.ok(responses);
    }

    /**
     * 좌석 예약
     */
    @Override
    @PostMapping("{concertScheduleId}/seat/{concertSeatId}")
    public ResponseEntity<Void> reserveConcert(
            @RequestHeader String token,
            @PathVariable Long concertScheduleId,
            @PathVariable Long concertSeatId
    ) {
        return ResponseEntity.ok().build();
    }

    /**
     * 콘서트 좌석 결제
     */
    @Override
    @PostMapping("/pay/{reservationId}")
    public ResponseEntity<ReservationPayResponse> payConcert(
            @RequestHeader String token,
            @RequestBody @Valid ReservationSeatRequest request
    ) {
        return ResponseEntity.ok(
                new ReservationPayResponse(1L, 1L, 1, 30000L)
        );
    }
}
