package com.hanghae.concert.api.common.exception;


import lombok.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SERVER_ERROR("5000"),
    BUSINESS_ERROR("4000"),
    AUTH_ERROR("403"),
    ;

    private final String errorNumber;
}
