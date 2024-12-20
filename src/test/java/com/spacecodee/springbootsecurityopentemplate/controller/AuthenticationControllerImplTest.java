package com.spacecodee.springbootsecurityopentemplate.controller;

import com.spacecodee.springbootsecurityopentemplate.controller.api.auth.impl.AuthenticationControllerImpl;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidCredentialsException;
import com.spacecodee.springbootsecurityopentemplate.service.auth.IAuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerImplTest {

    @Mock
    private IAuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationControllerImpl authenticationController;

    @Test
    void whenInvalidCredentials_thenReturnsUnauthorized() {
        // Given
        var loginVO = new LoginUserVO("testuser", "wrongpass");
        String locale = "es";

        when(authenticationService.login(locale, loginVO))
                .thenThrow(new InvalidCredentialsException(
                        "auth.invalid.credentials",
                        locale,
                        loginVO.username()));

        // When & Then
        var exception = assertThrows(InvalidCredentialsException.class,
                () -> authenticationController.authenticate(locale, loginVO));

        assertEquals("auth.invalid.credentials", exception.getMessageKey());
        assertEquals(locale, exception.getLocale());
        assertArrayEquals(new Object[]{loginVO.username()}, exception.getParameters());
    }
}