package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.payment.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("잔고 충전할 때 마이너스 또는 0원을 충전하면 에러가 발생한다.")
    void chargeMinusAndZeroException() {

        //given
        Member member = new Member(1L, 0);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            member.changeBalance(0, PaymentType.CHARGE);
        });

        //then
        assertEquals("마이너스 금액 또는 0원을 충전할 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("잔고 사용할 때 마이너스 또는 0원을 사용하면 에러가 발생한다.")
    void useMinusAndZeroException() {

        //given
        Member member = new Member(1L, 0);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            member.changeBalance(0, PaymentType.USE);
        });

        //then
        assertEquals("마이너스 금액 또는 0원을 사용할 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("잔고가 부족하면 예외가 발생한다.")
    void notEnough() {

        int useAmount = 1;

        //given
        Member member = new Member(1L, 0);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            member.changeBalance(useAmount, PaymentType.USE);
        });

        //then
        assertEquals("차감할 포인트가 부족합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("잔고 충전할 때 0원 이상을 충전할 수 있다.")
    void chargeOk() {

        int chartAmount = 10000;

        //given
        Member member = new Member(1L, 0);

        //when
        member.changeBalance(chartAmount, PaymentType.CHARGE);

        //then
        assertThat(member.getBalance()).isEqualTo(chartAmount);
    }

}