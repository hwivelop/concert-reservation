package com.hanghae.concert.api.common.exception;

import lombok.*;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(final String message) {
        super(message);
        this.code = "4000";
    }
}
