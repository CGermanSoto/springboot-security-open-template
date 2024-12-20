package com.spacecodee.springbootsecurityopentemplate.exceptions.handler;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorPojo;
import com.spacecodee.springbootsecurityopentemplate.data.record.ValidationError;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidCredentialsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidPasswordComplexityException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.UnauthorizedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.ModuleNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.OperationNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.PermissionNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.LastAdminException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.LastDeveloperException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.LastTechnicianException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.UserNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.PasswordDoNotMatchException;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageUtilComponent messageUtilComponent;
    private final MessageParameterHandler messageParameterHandler;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorPojo> handleBusinessException(@NotNull BaseException ex,
            HttpServletRequest request) {
        log.error("Business exception occurred: {}", ex.getMessage());
        return ResponseEntity
                .status(determineHttpStatus(ex))
                .body(createErrorResponse(ex, request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDataPojo<List<ValidationError>>> handleValidationException(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpServletRequest request) {
        String locale = request.getLocale().toString();

        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> createValidationError(error, locale))
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorDataPojo.of(
                        ex.getClass().getSimpleName(),
                        this.messageParameterHandler.createErrorMessage(
                                "validation.error",
                                locale,
                                errors.size()),
                        request.getRequestURI(),
                        request.getMethod(),
                        errors));
    }

    private @NotNull ValidationError createValidationError(@NotNull FieldError error, String locale) {
        // Get just the key without parameters
        String messageTemplate = this.extractMessageKey(error.getDefaultMessage());
        log.info("Message template: {}", messageTemplate);

        // Extract validation parameters
        Object[] validationParams = this.extractValidationParams(error);

        // Get message from properties file
        String message = "Size".equals(error.getCode())
                ? this.messageUtilComponent.getMessage("validation.user." + error.getField() + ".size", locale,
                        validationParams)
                : this.messageUtilComponent.getMessage(messageTemplate, locale, validationParams);

        return new ValidationError(
                error.getField(),
                error.getRejectedValue(),
                message,
                validationParams);
    }

    private Object[] extractValidationParams(@NotNull FieldError error) {
        if ("Size".equals(error.getCode())) {
            return new Object[] {
                    error.getArguments()[2], // min -> {0}
                    error.getArguments()[1] // max -> {1}
            };
        }
        return new Object[] { error.getRejectedValue() };
    }

    private @NotNull String extractMessageKey(@NotNull String defaultMessage) {
        if (defaultMessage != null && defaultMessage.startsWith("{") && defaultMessage.endsWith("}")) {
            return defaultMessage.substring(1, defaultMessage.length() - 1);
        }
        return defaultMessage;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorPojo> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createGenericErrorResponse(ex, request));
    }

    @ExceptionHandler(InvalidPasswordComplexityException.class)
    public ResponseEntity<ApiErrorPojo> handlePasswordComplexityException(
            @NotNull InvalidPasswordComplexityException ex,
            @NotNull HttpServletRequest request) {
        String locale = request.getLocale().toString();
        StringBuilder messageBuilder = new StringBuilder();

        // Add requirements header
        messageBuilder.append(this.messageUtilComponent.getMessage(
                "validation.password.requirements",
                locale));

        if (ex.getMessage() != null) {
            String[] messageKeys = ex.getMessage().split("\\|");

            for (String messageKey : messageKeys) {
                String[] parts = messageKey.split(",");
                if (parts.length > 1) {
                    Object[] params = new Object[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        params[i - 1] = Integer.parseInt(parts[i]);
                    }
                    String message = this.messageUtilComponent.getMessage(parts[0], locale, params);
                    messageBuilder.append("\n").append(message);
                }
            }
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorPojo.of(
                        ex.getClass().getSimpleName(),
                        messageBuilder.toString(),
                        request.getRequestURI(),
                        request.getMethod()));
    }

    private @NotNull ApiErrorPojo createErrorResponse(@NotNull BaseException ex,
            @NotNull HttpServletRequest request) {
        String userMessage = messageUtilComponent.getMessage(ex.getMessage(), ex.getLocale());
        String technicalDetails = String.format(
                "Exception: %s, Key: %s, Locale: %s, Path: %s, Method: %s",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex.getLocale(),
                request.getRequestURI(),
                request.getMethod());

        return ApiErrorPojo.of(
                technicalDetails,
                userMessage,
                request.getRequestURI(),
                request.getMethod());
    }

    private @NotNull ApiErrorPojo createGenericErrorResponse(@NotNull Exception ex,
            @NotNull HttpServletRequest request) {
        return ApiErrorPojo.of(
                ex.getLocalizedMessage(),
                messageUtilComponent.getMessage("error.unexpected", "en"),
                request.getRequestURI(),
                request.getMethod());
    }

    @Contract(pure = true)
    private HttpStatus determineHttpStatus(@NotNull BaseException ex) {
        return switch (ex) {
            case LastAdminException ignored -> HttpStatus.CONFLICT;
            case LastTechnicianException ignored -> HttpStatus.CONFLICT;
            case LastDeveloperException ignored -> HttpStatus.CONFLICT;
            case InvalidCredentialsException ignored -> HttpStatus.UNAUTHORIZED;
            case TokenExpiredException ignored -> HttpStatus.UNAUTHORIZED;
            case UnauthorizedException ignored -> HttpStatus.FORBIDDEN;
            case UserNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case ModuleNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case OperationNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case PermissionNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case AlreadyExistsException ignored -> HttpStatus.BAD_REQUEST;
            case InvalidParameterException ignored -> HttpStatus.BAD_REQUEST;
            case PasswordDoNotMatchException ignored -> HttpStatus.BAD_REQUEST;
            case NoContentException ignored -> HttpStatus.NO_CONTENT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
