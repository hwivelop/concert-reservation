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
    @Operation(summary = "대기열 생성", description = "회원의 대기열을 생성합니다.")
    @PostMapping("/{memberId}")
    public ResponseEntity<MemberQueueCreateResponse> createMemberQueue(
            @Parameter(description = "대기열을 생성할 회원의 ID", required = true)
            @PathVariable(name = "memberId") Long memberId
    ) {
        return ResponseEntity.ok(
                new MemberQueueCreateResponse(1L, UUID.randomUUID().toString(), LocalDateTime.now().plusMinutes(5))
        );
    }

    /**
     * 나의 대기 순번 조회 API
     */
    @Operation(summary = "나의 대기 순번 조회", description = "나의 대기 순번을 조회합니다.")
    @GetMapping("/my-turn")
    public ResponseEntity<MemberQueueMyTurnResponse> getMyTurn(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(new MemberQueueMyTurnResponse(1L, 1));
    }
}
