package com.hanghae.concert.domain.member;

import com.hanghae.concert.domain.*;
import com.hanghae.concert.domain.payment.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "balance", nullable = false)
    private Integer balance = 0;


    public void changeBalance(Integer balance, PaymentType paymentType) {

        if (paymentType == PaymentType.CHARGE) {
            validateCharge(balance);
        } else if (paymentType == PaymentType.USE) {
            validateUse(balance);
        }

        this.balance = balance;
    }

    private void validateCharge(Integer balance) {

        if (balance <= 0) {
            throw new IllegalArgumentException("마이너스 금액 또는 0원을 충전할 수 없습니다.");
        }
    }

    private void validateUse(Integer balance) {

        if (balance <= 0) {
            throw new IllegalArgumentException("마이너스 금액 또는 0원을 사용할 수 없습니다.");
        }

        if (this.balance < balance) {
            throw new IllegalArgumentException("차감할 포인트가 부족합니다.");
        }
    }
}
