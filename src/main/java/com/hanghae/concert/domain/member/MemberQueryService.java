package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.member.exception.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
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

        log.info("memberId = {}", memberId);
        return memberRepository.existsById(memberId);
    }
}
