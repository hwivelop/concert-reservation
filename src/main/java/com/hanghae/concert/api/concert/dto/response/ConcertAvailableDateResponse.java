package com.hanghae.concert.api.concert.dto.response;

import com.hanghae.concert.domain.concert.schedule.dto.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public record ConcertAvailableDateResponse(
        Long concertId,
        LocalDateTime startAt
) {
    public static List<ConcertAvailableDateResponse> toResponse(List<ConcertScheduleDto> concertScheduleDtos) {

        return concertScheduleDtos.stream()
                .map(dto -> new ConcertAvailableDateResponse(
                        dto.concertId(),
                        dto.startAt()
                )).collect(Collectors.toList());
    }
}
