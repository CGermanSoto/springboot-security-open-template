package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Getter
public non-sealed class ApiResponsePojo extends BaseResponsePojo {
    private final String message;
    private final HttpStatus httpStatus;

    protected ApiResponsePojo(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Contract("_, _ -> new")
    public static @NotNull ApiResponsePojo of(String message, HttpStatus status) {
        return new ApiResponsePojo(message, status);
    }

    public int getStatus() {
        return httpStatus.value();
    }
}