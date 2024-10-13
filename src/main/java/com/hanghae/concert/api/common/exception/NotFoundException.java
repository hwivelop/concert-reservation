package com.hanghae.concert.api.common.exception;

public abstract class NotFoundException extends BusinessException {

    public NotFoundException(final String message) {
        super(message);
    }

}
