package com.hanghae.concert.api.concert.dto.request;

import com.hanghae.concert.application.dto.*;

public record PayRequest(
        Long memberId,
        Long concertId,
        Long reservationId
) {

    public PaymentHistoryCreateDto toRequest() {

        return new PaymentHistoryCreateDto(
                memberId,
                concertId,
                reservationId
        );
    }
}
