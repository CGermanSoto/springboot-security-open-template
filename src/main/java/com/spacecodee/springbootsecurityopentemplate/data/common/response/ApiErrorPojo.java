package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
public non-sealed class ApiErrorPojo extends BaseResponsePojo {
    private final String backendMessage;
    private final String message;
    private final String path;
    private final String method;

    protected ApiErrorPojo(String backendMessage, String message, String path, String method) {
        super();
        this.backendMessage = backendMessage;
        this.message = message;
        this.path = path;
        this.method = method;
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull ApiErrorPojo of(String backendMessage, String message, String path, String method) {
        return new ApiErrorPojo(backendMessage, message, path, method);
    }
}