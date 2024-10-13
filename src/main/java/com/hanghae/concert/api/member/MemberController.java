package com.hanghae.concert.api.member;

import com.hanghae.concert.api.member.dto.request.*;
import com.hanghae.concert.api.member.dto.response.*;
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
    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMember(
            @RequestHeader String token
    ) {
        return ResponseEntity.ok(
                new MemberResponse(1L, 50000L)
        );
    }

    /**
     * 특정 유저 충전
     */
    @PostMapping("/charge")
    public ResponseEntity<MemberResponse> chargeBalance(
            @RequestHeader String token,
            @RequestBody @Valid MemberChargeRequest request
    ) {
        return ResponseEntity.ok(
                new MemberResponse(1L, 50000L)
        );
    }
}