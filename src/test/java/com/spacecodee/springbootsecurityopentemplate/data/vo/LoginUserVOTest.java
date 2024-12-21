package com.spacecodee.springbootsecurityopentemplate.data.vo;

import com.spacecodee.springbootsecurityopentemplate.constants.ValidationConstants;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginUserVOTest extends BaseVOTest {

    @Test
    void whenPasswordTooShort_thenValidationFails() {
        // Given
        var loginVO = new LoginUserVO("validuser", "short");

        // When
        Set<ConstraintViolation<LoginUserVO>> violations = validator.validate(loginVO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<LoginUserVO> violation = violations.iterator().next();
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals("{" + ValidationConstants.Messages.PASSWORD_SIZE + "}",
                violation.getMessageTemplate());
    }

    @Test
    void whenValidCredentials_thenValidationPasses() {
        var loginVO = new LoginUserVO("validuser", "validpass123");
        var violations = validator.validate(loginVO);

        assertTrue(violations.isEmpty());
    }
}