package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
public final class ApiErrorDataPojo<E> extends ApiErrorPojo {
    private final transient E data;

    private ApiErrorDataPojo(String backendMessage, String message, String path, String method, E data) {
        super(backendMessage, message, path, method);
        this.data = data;
    }

    @Contract("_, _, _, _, _ -> new")
    public static <E> @NotNull ApiErrorDataPojo<E> of(
            String backendMessage, String message, String path, String method, E data) {
        return new ApiErrorDataPojo<>(backendMessage, message, path, method, data);
    }

}