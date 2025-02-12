package com.spacecodee.springbootsecurityopentemplate.data.record;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

public record ValidationError(
        String field,
        Object rejectedValue,
        String message,
        Object[] parameters) {

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(rejectedValue, that.rejectedValue) &&
                Objects.equals(message, that.message) &&
                Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(field, rejectedValue, message);
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "ValidationError[" +
                "field=" + field + ", " +
                "rejectedValue=" + rejectedValue + ", " +
                "message=" + message + ", " +
                "parameters=" + Arrays.toString(parameters) + ']';
    }

    @JsonIgnore
    public Object getRawRejectedValue() {
        return rejectedValue;
    }

    @JsonValue
    public @Nullable String getRejectedValue() {
        if (rejectedValue instanceof MultipartFile multipartFile) {
            return multipartFile.getOriginalFilename();
        }
        return rejectedValue != null ? rejectedValue.toString() : null;
    }
}
