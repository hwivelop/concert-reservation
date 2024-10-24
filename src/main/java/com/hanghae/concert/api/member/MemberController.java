package com.hanghae.concert.api.member;

import com.hanghae.concert.api.common.response.*;
import com.hanghae.concert.api.member.dto.request.*;
import com.hanghae.concert.api.member.dto.response.*;
import com.hanghae.concert.application.*;
import com.hanghae.concert.application.dto.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 유저 생성
     */
    @PostMapping
    public BasicResponse<MemberResponse> createMember() {

        MemberDto memberDto = memberService.createMember();

        return BasicResponse.ok(
                MemberResponse.toResponse(memberDto)
        );
    }

    /**
     * 특정 유저 조회(잔액 조회)
     */
    @GetMapping("/balance")
    public BasicResponse<MemberResponse> getMember(@RequestParam("memberId") Long memberId) {

        log.info("memberId = {}", memberId);

        MemberDto memberDto = memberService.getMemberById(memberId);

        return BasicResponse.ok(
                MemberResponse.toResponse(memberDto)
        );
    }

    /**
     * 특정 유저의 잔액 사용/충전
     */
    @PostMapping("/balance")
    public BasicResponse<Void> updateBalance(@RequestBody @Valid MemberUpdateBalanceRequest request) {

        memberService.updateMemberBalance(request.toApplication());

        return BasicResponse.ok();
    }
}