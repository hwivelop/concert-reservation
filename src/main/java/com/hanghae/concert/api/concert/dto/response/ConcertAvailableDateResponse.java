package com.hanghae.concert.api.concert.dto.response;

import java.time.*;

public record ConcertAvailableDateResponse(
        Long concertId,
        LocalDateTime startAt
) {
}
