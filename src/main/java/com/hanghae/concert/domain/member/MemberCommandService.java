package com.hanghae.concert.domain.member;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.member.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public Member initMember(Member member) {

       return memberRepository.save(member);
    }

    public void updateBalance(MemberUpdateBalanceDto dto) {

        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(MemberNotFoundException::new);

        member.changeBalance(dto.amount(), dto.paymentType());

        memberRepository.save(member);
    }
}
