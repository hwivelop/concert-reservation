package com.hanghae.concert.api.concert.dto.response;

import com.hanghae.concert.domain.concert.seat.dto.*;

import java.util.*;
import java.util.stream.*;

public record ConcertAvailableSeatResponse(
        Long concertId,
        Long concertScheduleId,
        Integer seatNumber
) {
    public static List<ConcertAvailableSeatResponse> toResponse(Long concertId, List<ConcertSeatDto> concertSeatDtos) {

        if (concertSeatDtos == null || concertSeatDtos.isEmpty()) {
            return Collections.emptyList();
        }

        return concertSeatDtos.stream()
                .map(dto -> new ConcertAvailableSeatResponse(
                        concertId,
                        dto.concertScheduleId(),
                        dto.seatNumber()
                )).collect(Collectors.toList());
    }
}
