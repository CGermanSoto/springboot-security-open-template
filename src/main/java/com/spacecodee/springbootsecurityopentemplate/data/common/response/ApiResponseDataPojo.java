package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Getter
public final class ApiResponseDataPojo<T> extends ApiResponsePojo {
    private final T data;

    private ApiResponseDataPojo(String message, HttpStatus httpStatus, T data) {
        super(message, httpStatus);
        this.data = data;
    }

    @Contract("_, _, _ -> new")
    public static <T> @NotNull ApiResponseDataPojo<T> of(String message, HttpStatus status, T data) {
        return new ApiResponseDataPojo<>(message, status, data);
    }

}