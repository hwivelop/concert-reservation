package com.hanghae.concert.application;

import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.payment.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.math.*;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public void updateMemberBalance(Long memberId, Long balance, PaymentType paymentType) {

        // 유저 검증
        if (memberQueryService.existsMemberById(memberId)) {
            throw new MemberNotFoundException();
        }

        memberCommandService.updateBalance(memberId, balance, paymentType);

    }
}
