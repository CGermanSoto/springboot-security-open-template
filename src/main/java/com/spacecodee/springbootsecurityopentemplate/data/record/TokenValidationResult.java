package com.spacecodee.springbootsecurityopentemplate.data.record;

public record TokenValidationResult(
        String token,
        boolean wasRefreshed) {
}