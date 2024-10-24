package com.hanghae.concert.api.concert;

import com.hanghae.concert.api.common.response.*;
import com.hanghae.concert.api.concert.dto.request.*;
import com.hanghae.concert.api.concert.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public interface IConcertController {

    /**
     * 콘서트 생성
     */
    @Operation(summary = "콘서트 정보 생성", description = "콘서트와 콘서트 스케쥴을 생성합니다.")
    @PostMapping
    public BasicResponse<Void> createConcert(
            @RequestBody CreateConcertRequest request
    );

    /**
     * 예약 가능 날짜 조회
     */
    @Operation(summary = "예약 가능 날짜 조회", description = "특정 콘서트의 예약 가능한 날짜를 조회합니다.")
    BasicResponse<List<ConcertAvailableDateResponse>> getConcertSchedules(
            @PathVariable(name = "concertId") Long concertId,
            @Parameter(description = "고객 ID", required = true) Long memberId
    );

    /**
     * 예약 가능 좌석 조회
     */
    @Operation(summary = "예약 가능 좌석 조회", description = "예약 가능한 좌석을 조회합니다.")
    @GetMapping("{concertId}/schedule/{concertScheduleId}/seats")
    BasicResponse<List<ConcertAvailableSeatResponse>> getConcertSeats(
            @Parameter(description = "고객 ID", required = true) Long memberId,
            @Parameter(description = "콘서트 ID", required = true)
            @PathVariable(name = "concertId") Long concertId,
            @Parameter(description = "콘서트 스케쥴 ID", required = true)
            @PathVariable(name = "concertScheduleId") Long concertScheduleId
    );

    /**
     * 좌석 예약
     */
    @Operation(summary = "좌석 예약", description = "좌석을 예약합니다.")
    @PostMapping("{concertScheduleId}/seat/{concertSeatId}")
    BasicResponse<Void> reserveConcert(
            @RequestBody ReservationSeatRequest request
    );

    /**
     * 콘서트 좌석 결제
     */
    @Operation(summary = "콘서트 좌석 결제", description = "예약한 콘서트 좌석에 대한 결제를 진행합니다.")
    @PostMapping("/pay/{reservationId}")
    BasicResponse<ReservationPayResponse> payConcert(
            @Parameter(description = "결제에 필요한 요청 데이터", required = true)
            @RequestBody @Valid PayRequest request
    );
}
