package com.spacecodee.springbootsecurityopentemplate.data.record;

import java.util.Map;

public record TokenClaims(
        String subject,
        Map<String, Object> claims) {
}
