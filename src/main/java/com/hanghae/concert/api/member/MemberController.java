package com.hanghae.concert.api.member;

import com.hanghae.concert.api.member.dto.request.*;
import com.hanghae.concert.api.member.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    /**
     * 특정 유저 조회(잔액 조회)
     */
    @Operation(summary = "특정 유저 조회", description = "특정 회원의 잔액 정보를 조회합니다.")
    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMember(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(
                new MemberResponse(1L, 50000L)
        );
    }

    /**
     * 특정 유저 충전
     */
    @Operation(summary = "특정 유저 충전", description = "특정 회원의 잔액을 충전합니다.")
    @PostMapping("/charge")
    public ResponseEntity<MemberResponse> chargeBalance(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token,

            @Parameter(description = "충전할 금액 정보", required = true)
            @RequestBody @Valid MemberChargeRequest request
    ) {
        return ResponseEntity.ok(
                new MemberResponse(1L, 50000L)
        );
    }
}