package com.spacecodee.springbootsecurityopentemplate.service.security.token.builder;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;

import javax.crypto.SecretKey;

public interface ITokenBuilderService {

    String build(TokenClaims tokenClaims, SecretKey key);

}
