package com.hanghae.concert.api.queue;

import com.hanghae.concert.api.common.response.*;
import com.hanghae.concert.api.queue.dto.request.*;
import com.hanghae.concert.api.queue.dto.response.*;
import com.hanghae.concert.application.*;
import com.hanghae.concert.domain.member.queue.dto.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class MemberQueueController {

    private final MemberQueueService memberQueueService;

    /**
     * 대기열 생성 API
     */
    @PostMapping
    public BasicResponse<MemberQueueCreateResponse> createMemberQueue(
            @RequestBody MemberQueueCreateRequest request
    ) {

        MemberQueueDto memberQueueDto = memberQueueService.createToken(request.toRequest());

        return BasicResponse.ok(
                MemberQueueCreateResponse.toResponse(memberQueueDto)
        );
    }

    /**
     * 나의 대기 순번 조회 API
     */
    @GetMapping("/my-turn")
    public BasicResponse<Long> getMyTurn(
            @RequestHeader(name = "Authorization") String token
    ) {

        return BasicResponse.ok(
                memberQueueService.getMyTurn(token)
        );
    }
}
