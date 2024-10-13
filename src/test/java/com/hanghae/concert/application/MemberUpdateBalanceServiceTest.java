package com.hanghae.concert.application;

import com.hanghae.concert.domain.member.*;
import com.hanghae.concert.domain.payment.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.annotation.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberUpdateBalanceServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCommandService memberCommandService;

    @Autowired
    private MemberRepository memberRepository;

    Long memberId;

    @BeforeEach
    void setUp() {
        Member member = new Member(1L, 100);  // 처음에 잔액이 0인 회원을 생성
        memberCommandService.initMember(member);

        memberId = member.getId();
    }

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    @DirtiesContext //스프링 컨텍스트 새로고침
    @DisplayName("고객의 잔액을 충전한다.")
    void chargeBalance() {

        // given
        int chargeAmount = 100;
        int result = 200;
        PaymentType paymentType = PaymentType.CHARGE;

        // when
        memberService.updateMemberBalance(memberId, chargeAmount, paymentType);

        // then
        Member updatedMember = memberRepository.findById(memberId).orElse(null);

        assertThat(updatedMember.getBalance()).isEqualTo(result);
    }

    @Test
    @DirtiesContext //스프링 컨텍스트 새로고침
    @DisplayName("고객의 잔액을 사용한다.")
    void useBalance() {

        // given
        int useAmount = 100;
        int result = 0;
        PaymentType paymentType = PaymentType.USE;

        // when
        memberService.updateMemberBalance(memberId, useAmount, paymentType);

        // then
        Member updatedMember = memberRepository.findById(memberId).orElse(null);

        assertThat(updatedMember.getBalance()).isEqualTo(result);
    }
}