package com.hanghae.concert.api.queue.dto.response;

import java.time.*;

public record MemberQueueMyTurnResponse(
        Long memberId,
        Integer myTurn
) {
}
