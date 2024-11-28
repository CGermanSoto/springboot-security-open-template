// GlobalExceptionHandler.java
package com.spacecodee.springbootsecurityopentemplate.controller.advice;

import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.CannotSaveException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoDeletedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoUpdatedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.RoleNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.UserNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageUtilComponent messageUtilComponent;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(@NotNull MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponsePojo> handleBadCredentials(BadCredentialsException ex) {
        return createErrorResponse("auth.invalid.credentials", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponsePojo> handleAccessDenied(AccessDeniedException ex) {
        return createErrorResponse("auth.access.denied", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            TokenNotFoundException.class,
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<ApiResponsePojo> handleNotFoundException(@NotNull RuntimeException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CannotSaveException.class,
            NoDeletedException.class,
            NoUpdatedException.class
    })
    public ResponseEntity<ApiResponsePojo> handleOperationException(@NotNull RuntimeException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponsePojo> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        return createErrorResponse("error.unexpected", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Contract("_, _ -> new")
    private @NotNull ResponseEntity<ApiResponsePojo> createErrorResponse(String messageKey, HttpStatus status) {
        var response = new ApiResponsePojo();
        response.setMessage(this.messageUtilComponent.getMessage(messageKey, "en"));
        response.setHttpStatus(status);
        return new ResponseEntity<>(response, status);
    }
}