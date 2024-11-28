package com.spacecodee.springbootsecurityopentemplate.controller.base;

import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public abstract class AbstractController {
    protected final MessageUtilComponent messageUtilComponent;

    protected ApiResponsePojo createResponse(String messageKey, String locale, HttpStatus status) {
        var response = new ApiResponsePojo();
        response.setMessage(this.messageUtilComponent.getMessage(messageKey, locale));
        response.setHttpStatus(status);
        return response;
    }

    protected <T> ApiResponseDataPojo<T> createDataResponse(T data, String messageKey, String locale,
                                                            HttpStatus status) {
        var response = new ApiResponseDataPojo<T>();
        response.setData(data);
        response.setMessage(this.messageUtilComponent.getMessage(messageKey, locale));
        response.setHttpStatus(status);
        return response;
    }
}