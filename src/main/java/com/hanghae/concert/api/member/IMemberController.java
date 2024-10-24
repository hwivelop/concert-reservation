package com.hanghae.concert.api.member;

import com.hanghae.concert.api.common.response.*;
import com.hanghae.concert.api.member.dto.request.*;
import com.hanghae.concert.api.member.dto.response.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

public interface IMemberController {

    /**
     * 특정 유저 조회(잔액 조회)
     */
    @Operation(summary = "특정 유저 조회", description = "특정 회원의 잔액 정보를 조회합니다.")
    BasicResponse<MemberResponse> getMember(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token
    );

    /**
     * 특정 유저 충전
     */
    @Operation(summary = "특정 유저 충전", description = "특정 회원의 잔액을 충전합니다.")
    BasicResponse<MemberResponse> chargeBalance(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "충전할 금액 정보", required = true)
            @RequestBody @Valid MemberUpdateBalanceRequest request
    );
}