package com.hanghae.concert.domain.member.queue;

public enum TokenStatus {

    WAIT, //대기자
    ACTIVE, // 입장가능
    DONE, // 결제완료자 --> 남길 생각.
    EXPIRED, // 만료자 --> 배치가  돌면서 삭제할 예정
}
