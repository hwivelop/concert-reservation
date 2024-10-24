package com.hanghae.concert.application;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.exception.*;
import lombok.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public void updateMemberBalance(MemberUpdateBalanceDto dto) {

        // 유저 검증
        if (!memberQueryService.existsMemberById(dto.memberId())) {
            throw new MemberNotFoundException();
        }

        memberCommandService.updateBalance(dto);
    }

    public MemberDto getMemberById(Long memberId) {

        return MemberDto.of(
                memberQueryService.getMemberById(memberId)
        );
    }

    public MemberDto createMember() {

        return MemberDto.of(
                memberCommandService.initMember(
                        new Member(null, 0)
                )
        );
    }
}
