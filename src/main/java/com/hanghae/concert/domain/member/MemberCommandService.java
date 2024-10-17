package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.member.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public MemberDto initMember() {

        Member member = memberRepository.save(
                new Member(
                        null,
                        0L
                )
        );

        return MemberDto.of(member);
    }
}
