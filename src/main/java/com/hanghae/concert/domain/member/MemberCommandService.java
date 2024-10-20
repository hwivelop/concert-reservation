package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.member.dto.*;
import com.hanghae.concert.domain.member.exception.*;
import com.hanghae.concert.domain.payment.*;
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

    public void updateBalance(Long memberId, Integer balance, PaymentType paymentType) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.changeBalance(balance, paymentType);

        memberRepository.save(member);
    }
}
