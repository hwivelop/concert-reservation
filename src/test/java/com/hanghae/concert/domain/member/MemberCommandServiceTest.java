package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.payment.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberCommandService memberCommandService;

    @Test
    @DisplayName("충전을 하면 유저의 잔액이 충전된다.")
    void chargeBalance() {

        //given
        Long memberId = 1L;
        int init = 0;
        int balance = 1000;
        int result = 1000;
        PaymentType paymentType = PaymentType.CHARGE;

        Member member = new Member(memberId, init);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        memberCommandService.updateBalance(memberId, balance, paymentType);

        // then
        verify(memberRepository).save(member);
        assertEquals(result, member.getBalance());
    }

    @Test
    @DisplayName("사용을 하면 유저의 잔액이 차감된다.")
    void useBalance() {

        //given
        Long memberId = 1L;
        int init = 1000;
        int balance = 500;
        int result = 500;
        PaymentType paymentType = PaymentType.USE;

        Member member = new Member(memberId, init);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        memberCommandService.updateBalance(memberId, balance, paymentType);

        // then
        verify(memberRepository).save(member);
        assertEquals(result, member.getBalance());
    }
}