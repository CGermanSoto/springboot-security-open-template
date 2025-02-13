package com.spacecodee.springbootsecurityopentemplate.exceptions.handler;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorPojo;
import com.spacecodee.springbootsecurityopentemplate.data.record.ValidationError;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidCredentialsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.UnauthorizedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.*;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.ratelimit.RateLimitExceededException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.ModuleNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.OperationNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.PermissionNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoDisabledException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.*;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.PasswordDoNotMatchException;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageUtilComponent messageUtilComponent;

    @Value("${app.document.max-file-size:5}")
    private int maxFileSizeMB;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDataPojo<List<ValidationError>>> handleValidationException(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpServletRequest request) {

        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::createValidationError).toList();

        String errorMessage = errors.isEmpty() ? messageUtilComponent.getMessage("validation.error", "1") : errors.getFirst().message();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorDataPojo.of(ex.getClass().getSimpleName(), errorMessage, request.getRequestURI(), request.getMethod(), errors));
    }

    private @NotNull ValidationError createValidationError(@NotNull FieldError error) {
        Object rejectedValue = error.getRejectedValue();
        if (rejectedValue instanceof MultipartFile multipartFile) {
            rejectedValue = multipartFile.getOriginalFilename();
        }

        String message;
        String defaultMessage = error.getDefaultMessage();

        if (defaultMessage != null && !defaultMessage.startsWith("{")) {
            message = defaultMessage;
        } else if (defaultMessage != null && defaultMessage.startsWith("{") && defaultMessage.endsWith("}")) {
            String key = defaultMessage.substring(1, defaultMessage.length() - 1);
            message = messageUtilComponent.getMessage(key);
        } else {
            message = messageUtilComponent.getMessage("error.unknown");
        }

        return new ValidationError(error.getField(), rejectedValue, message, new Object[]{});
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorPojo> handleGenericException(Exception ex, HttpServletRequest request) {

        if (ex instanceof BaseException baseEx) {
            String userMessage = this.messageUtilComponent.getMessage(baseEx.getMessageKey(),
                    baseEx.getParameters());

            ApiErrorPojo errorResponse = ApiErrorPojo.of("Authentication Generic Error", userMessage,
                    request.getRequestURI(), request.getMethod());

            log.error("Authentication error: {}", userMessage);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        ApiErrorPojo errorResponse = ApiErrorPojo.of("Internal Server Error",
                this.messageUtilComponent.getMessage("error.internal.server"), request.getRequestURI(), request.getMethod());

        log.error("Unexpected error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorPojo> handleHttpMessageNotReadableException(
            @NotNull HttpMessageNotReadableException ex, @NotNull HttpServletRequest request) {

        ApiErrorPojo errorResponse = ApiErrorPojo.of("Message Not Readable",
                this.messageUtilComponent.getMessage("error.message.not.readable"), request.getRequestURI(), request.getMethod());

        log.error("Message not readable: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<ApiErrorPojo> handleTokenInvalidException(
            @NotNull JwtTokenInvalidException ex,
            @NotNull HttpServletRequest request) {

        ApiErrorPojo errorResponse = ApiErrorPojo.of("Authentication Error",
                this.messageUtilComponent.getMessage(ex.getMessageKey(), ex.getParameters()), request.getRequestURI(), request.getMethod());

        log.debug("Token validation failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ApiErrorPojo> handleTokenException(
            @NotNull JwtTokenException ex,
            @NotNull HttpServletRequest request) {

        String resolvedMessage = this.messageUtilComponent.getMessage(ex.getMessageKey(), ex.getParameters());

        ApiErrorPojo errorResponse = ApiErrorPojo.of("Authentication Error", resolvedMessage,
                request.getRequestURI(), request.getMethod());

        log.error("Token validation failed: {}", resolvedMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorPojo> handleBusinessException(
            @NotNull BaseException ex,
            @NotNull HttpServletRequest request) {

        String resolvedMessage = this.messageUtilComponent.getMessage(ex.getMessageKey(), ex.getParameters());

        ApiErrorPojo errorResponse = ApiErrorPojo.of(ex.getClass().getSimpleName(), resolvedMessage,
                request.getRequestURI(), request.getMethod());

        log.error("Business exception: {}", resolvedMessage);
        return ResponseEntity.status(determineHttpStatus(ex)).body(errorResponse);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorPojo> handleMaxUploadSizeExceededException(
            @NotNull MaxUploadSizeExceededException ex,
            @NotNull HttpServletRequest request) {

        ApiErrorPojo errorResponse = ApiErrorPojo.of(
                "Max Upload Size Exceeded",
                this.messageUtilComponent.getMessage("document.file.max.size.exceeded",
                        String.valueOf(maxFileSizeMB)),
                request.getRequestURI(), request.getMethod());

        log.error("File size exceeded: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Contract(pure = true)
    private HttpStatus determineHttpStatus(@NotNull BaseException ex) {
        return switch (ex) {
            case LastAdminException ignored -> HttpStatus.CONFLICT;
            case LastTechnicianException ignored -> HttpStatus.CONFLICT;
            case LastDeveloperException ignored -> HttpStatus.CONFLICT;
            case InvalidCredentialsException ignored -> HttpStatus.UNAUTHORIZED;
            case JwtTokenExpiredException ignored -> HttpStatus.UNAUTHORIZED;
            case UnauthorizedException ignored -> HttpStatus.FORBIDDEN;
            case UserNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case ModuleNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case OperationNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case PermissionNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case AlreadyExistsException ignored -> HttpStatus.BAD_REQUEST;
            case InvalidParameterException ignored -> HttpStatus.BAD_REQUEST;
            case PasswordDoNotMatchException ignored -> HttpStatus.BAD_REQUEST;
            case NoContentException ignored -> HttpStatus.NO_CONTENT;
            case RateLimitExceededException ignored -> HttpStatus.TOO_MANY_REQUESTS;
            case JwtTokenMalformedException ignored -> HttpStatus.BAD_REQUEST;
            case JwtTokenValidationException ignored -> HttpStatus.UNAUTHORIZED;
            case NoDisabledException ignored -> HttpStatus.BAD_REQUEST;
            case UsernameAlreadyExistsException ignored -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
