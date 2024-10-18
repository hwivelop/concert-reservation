package com.hanghae.concert.api.common.response;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Getter
@Schema(title = "API 기본 응답")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicResponse<T> {

    @Schema(description = "응답 코드", example = "0000")
    private String code;

    @Schema(description = "응답 메시지", example = "성공 또는 에러 메시지")
    private String message;

    @Schema(description = "응답 데이터", nullable = true)
    private T data;

    @Schema(description = "성공 응답 반환", example = "{\"code\": \"0000\", \"message\": \"성공\", \"data\": {}}")
    public static <T> BasicResponse<T> ok(final T data) {
        return new BasicResponse<>(
                "0000",
                "성공",
                data
        );
    }

    @Schema(description = "성공 응답 반환 (데이터 없음)", example = "{\"code\": \"0000\", \"message\": \"성공\", \"data\": null}")
    public static BasicResponse<Void> ok() {
        return ok(null);
    }

    @Schema(description = "에러 응답 반환", example = "{\"code\": \"4000\", \"message\": \"에러 메시지\", \"data\": null}")
    public static BasicResponse<Void> error(final String code, final String message) {
        return new BasicResponse<>(
                code,
                message,
                null
        );
    }
}
