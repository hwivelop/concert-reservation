package com.hanghae.concert.api.concert.dto.request;

import com.hanghae.concert.application.dto.*;

public record SearchDatesRequest(
        Long memberId,
        Long concertId
) {

    public SearchDatesDto toRequest() {

        return new SearchDatesDto(
                memberId,
                concertId
        );
    }
}
