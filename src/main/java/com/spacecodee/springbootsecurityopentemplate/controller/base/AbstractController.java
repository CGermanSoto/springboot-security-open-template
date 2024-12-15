package com.spacecodee.springbootsecurityopentemplate.controller.base;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractController {
    protected final MessageUtilComponent messageUtilComponent;
    protected final MessageParameterHandler messageParameterHandler;

    protected ApiResponsePojo createResponse(String messageKey, String locale, HttpStatus status) {
        log.debug("Creating response: messageKey='{}', locale='{}', status='{}'",
                messageKey, locale, status);
        return ApiResponsePojo.of(
                this.messageParameterHandler.createMessage(messageKey, locale),
                status);
    }

    protected ApiResponsePojo createResponse(String messageKey, String locale, HttpStatus status, Object... params) {
        log.debug("Creating parameterized response: messageKey='{}', locale='{}', status='{}', params='{}'",
                messageKey, locale, status, Arrays.toString(params));
        return ApiResponsePojo.of(
                this.messageParameterHandler.createMessage(messageKey, locale, params),
                status);
    }

    protected <T> ApiResponseDataPojo<T> createDataResponse(@Valid T data, String messageKey,
            String locale, HttpStatus status) {
        log.debug("Creating data response: messageKey='{}', locale='{}', status='{}', data='{}'",
                messageKey, locale, status, data);
        return ApiResponseDataPojo.of(
                this.messageParameterHandler.createMessage(messageKey, locale),
                status,
                data);
    }

    protected <T> ApiResponseDataPojo<T> createDataResponse(@Valid T data, String messageKey,
            String locale, HttpStatus status, Object... params) {
        log.debug("Creating parameterized data response: messageKey='{}', locale='{}', status='{}', params='{}'",
                messageKey, locale, status, Arrays.toString(params));
        return ApiResponseDataPojo.of(
                this.messageParameterHandler.createMessage(messageKey, locale, params),
                status,
                data);
    }
}