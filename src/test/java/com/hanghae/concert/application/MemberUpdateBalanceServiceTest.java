package com.hanghae.concert.application;

import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.payment.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

@SpringBootTest
class MemberUpdateBalanceServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCommandService memberCommandService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        // 고객 정보를 저장
        Member member = new Member(1L, 100);  // 처음에 잔액이 0인 회원을 생성
        memberCommandService.initMember(member);
    }

    @Test
    @DisplayName("고객의 잔액을 충전한다.")
    void chargeBalance() {

        // given
        Long memberId = 1L;
        int chargeAmount = 100;  // 충전할 금액
        PaymentType paymentType = PaymentType.CHARGE;  // 결제 타입은 '충전'으로 설정

        // when
        memberService.updateMemberBalance(memberId, chargeAmount, paymentType);

        // then
        Member updatedMember = memberRepository.findById(memberId).orElse(null);

        Assertions.assertEquals(chargeAmount, updatedMember.getBalance());
    }

    @Test
    @DisplayName("고객의 잔액을 사용한다.")
    void useBalance() {

        // given
        Long memberId = 1L;
        int useAmount = 100;  // 충전할 금액
        PaymentType paymentType = PaymentType.USE;  // 결제 타입은 '충전'으로 설정

        // when
        memberService.updateMemberBalance(memberId, useAmount, paymentType);

        // then
        Member updatedMember = memberRepository.findById(memberId).orElse(null);

        Assertions.assertEquals(useAmount, updatedMember.getBalance());
    }
}