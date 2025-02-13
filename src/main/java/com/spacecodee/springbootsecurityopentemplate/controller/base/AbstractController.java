package com.spacecodee.springbootsecurityopentemplate.controller.base;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractController {

    protected final MessageParameterHandler messageParameterHandler;

    protected ApiResponsePojo createResponse(String messageKey, HttpStatus status) {
        return ApiResponsePojo.of(
                this.messageParameterHandler.createMessage(messageKey),
                status);
    }

    protected ApiResponsePojo createResponse(String messageKey, HttpStatus status, Object... params) {
        return ApiResponsePojo.of(
                this.messageParameterHandler.createMessage(messageKey, params), status);
    }

    protected <T> ApiResponseDataPojo<T> createDataResponse(@Valid T data, String messageKey, HttpStatus status) {
        return ApiResponseDataPojo.of(
                this.messageParameterHandler.createMessage(messageKey),
                status,
                data);
    }

    protected <T> ApiResponseDataPojo<T> createDataResponse(@Valid T data, String messageKey, HttpStatus status, Object... params) {
        return ApiResponseDataPojo.of(
                this.messageParameterHandler.createMessage(messageKey, params),
                status,
                data);
    }

}