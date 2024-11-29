package com.spacecodee.springbootsecurityopentemplate.data.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public abstract sealed class BaseResponsePojo implements Serializable
        permits ApiResponsePojo, ApiErrorPojo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    protected BaseResponsePojo() {
        this.timestamp = LocalDateTime.now();
    }

}