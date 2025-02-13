package com.spacecodee.springbootsecurityopentemplate.service.security.token.key;

import javax.crypto.SecretKey;

import org.jetbrains.annotations.NotNull;

public interface IJwtKeyService {

    @NotNull SecretKey generateKey();

    boolean isValidKey(byte @NotNull [] key);

}
