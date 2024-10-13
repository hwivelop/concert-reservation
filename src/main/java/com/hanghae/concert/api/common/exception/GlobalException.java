package com.hanghae.concert.api.common.exception;

import com.hanghae.concert.api.common.response.*;
import lombok.extern.slf4j.*;
import org.springframework.http.converter.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.stream.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public BasicResponse<Void> exception(final Exception e) {
        e.printStackTrace();
        log.error("Internal Server Error: {} ", e.getMessage(), e);
        return BasicResponse.error("5000", "관리자에게 문의 바랍니다.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(OK)
    public BasicResponse<Void> httpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.info("HttpMessageNotReadableException: {}", e.getMessage());
        return BasicResponse.error("4000", "데이터 형식이 맞지 않습니다.");
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(OK)
    public BasicResponse<Void> ioException(final IOException e) {
        log.info("IOException: {}", e.getMessage());
        return BasicResponse.error("4000", e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(OK)
    public BasicResponse<Void> illegalException(final RuntimeException e) {
        log.error("{}: {}", e.getClass(), e.getMessage(), e);
        return BasicResponse.error("4000", e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    protected BasicResponse<Void> nullPointerException(NullPointerException e) {
        log.error("{}: {}", e.getClass(), e.getMessage(), e);
        return BasicResponse.error("4000", e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(OK)
    public BasicResponse<Void> bindException(final BindException e) {
        final String bindingErrorMessage = e.getFieldErrors()
                .stream()
                .map(GlobalException::getBindErrorMessage)
                .collect(Collectors.joining("\n"));
        log.info("BindException: {}", bindingErrorMessage);

        return BasicResponse.error("4000", bindingErrorMessage);
    }

    private static String getBindErrorMessage(final FieldError fieldError) {
        return String.format(
                "[필드 명: %s]: 실패 사유: %s - 요청 값: %s",
                fieldError.getField(),
                fieldError.getDefaultMessage(),
                fieldError.getRejectedValue()
        );
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(OK)
    public BasicResponse<Void> businessException(final BusinessException e) {
        log.error("BusinessException: {}", e.getMessage(), e);
        return BasicResponse.error(e.getCode(), e.getMessage());
    }
}
