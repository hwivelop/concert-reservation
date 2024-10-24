package com.hanghae.concert.api.concert.dto.request;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.concert.*;

import java.time.*;

public record CreateConcertRequest(
        String title,
        Integer capacity,
        Integer seatPrice,
        ConcertStatus concertStatus,
        LocalDateTime startAt
) {

    public CreateConcertDto toRequest() {

        return new CreateConcertDto(
                title,
                capacity,
                seatPrice,
                concertStatus,
                startAt
        );
    }
}
