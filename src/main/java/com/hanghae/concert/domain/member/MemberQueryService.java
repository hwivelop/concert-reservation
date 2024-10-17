package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.member.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member getMemberById(Long memberId) {

        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Boolean existsMemberById(Long memberId) {

        return memberRepository.existsById(memberId);
    }
}
