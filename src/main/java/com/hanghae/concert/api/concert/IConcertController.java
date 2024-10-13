package com.hanghae.concert.api.concert;

import com.hanghae.concert.api.concert.dto.request.*;
import com.hanghae.concert.api.concert.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public interface IConcertController {

    /**
     * 예약 가능 날짜 조회
     */
    @Operation(summary = "예약 가능 날짜 조회", description = "특정 콘서트의 예약 가능한 날짜를 조회합니다.")
    ResponseEntity<List<ConcertAvailableDateResponse>> getConcertSchedules(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token
    );

    /**
     * 예약 가능 좌석 조회
     */
    @Operation(summary = "예약 가능 좌석 조회", description = "예약 가능한 좌석을 조회합니다.")
    @GetMapping("/schedule/{concertScheduleId}/seats")
    ResponseEntity<List<ConcertAvailableSeatResponse>> getConcertSeats(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "콘서트 스케쥴 ID", required = true)
            @PathVariable(name = "concertScheduleId") Long concertScheduleId
    );

    /**
     * 좌석 예약
     */
    @Operation(summary = "좌석 예약", description = "좌석을 예약합니다.")
    @PostMapping("{concertScheduleId}/seat/{concertSeatId}")
    ResponseEntity<Void> reserveConcert(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "콘서트 스케쥴 ID", required = true)
            @PathVariable(name = "concertScheduleId") Long concertScheduleId,
            @Parameter(description = "콘서트 좌석 ID", required = true)
            @PathVariable(name = "concertSeatId") Long concertSeatId
    );

    /**
     * 콘서트 좌석 결제
     */
    @Operation(summary = "콘서트 좌석 결제", description = "예약한 콘서트 좌석에 대한 결제를 진행합니다.")
    @PostMapping("/pay/{reservationId}")
    ResponseEntity<ReservationPayResponse> payConcert(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "결제에 필요한 요청 데이터", required = true)
            @RequestBody @Valid ReservationSeatRequest request
    );
}
