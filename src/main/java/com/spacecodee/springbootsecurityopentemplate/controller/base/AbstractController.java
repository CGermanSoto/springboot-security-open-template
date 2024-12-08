package com.spacecodee.springbootsecurityopentemplate.controller.base;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public abstract class AbstractController {
    protected final MessageUtilComponent messageUtilComponent;

    protected ApiResponsePojo createResponse(String messageKey, String locale, HttpStatus status) {
        return ApiResponsePojo.of(
                this.messageUtilComponent.getMessage(messageKey, locale),
                status);
    }

    protected <T> ApiResponseDataPojo<T> createDataResponse(@Valid T data, String messageKey, String locale,
                                                            HttpStatus status) {
        return ApiResponseDataPojo.of(
                this.messageUtilComponent.getMessage(messageKey, locale),
                status,
                data);
    }
}