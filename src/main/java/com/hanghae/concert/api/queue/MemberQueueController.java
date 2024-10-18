package com.hanghae.concert.api.queue;

import com.hanghae.concert.api.queue.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class MemberQueueController {

    /**
     * 대기열 생성 API
     */
    @PostMapping("/{memberId}")
    public ResponseEntity<MemberQueueCreateResponse> createMemberQueue(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(
                new MemberQueueCreateResponse(1L, UUID.randomUUID().toString(), LocalDateTime.now().plusMinutes(5))
        );
    }

    /**
     * 나의 대기 순번 조회 API
     */
    @GetMapping("/my-turn")
    public ResponseEntity<MemberQueueMyTurnResponse> getMyTurn(
            @RequestHeader String token
    ) {
        return ResponseEntity.ok(new MemberQueueMyTurnResponse(1L, 1));
    }
}
