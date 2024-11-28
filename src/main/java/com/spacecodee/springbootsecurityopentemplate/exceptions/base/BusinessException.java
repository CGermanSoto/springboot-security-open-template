package com.spacecodee.springbootsecurityopentemplate.exceptions.base;

import lombok.Getter;

@Getter
public abstract class BusinessException extends BaseException {
    protected BusinessException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}