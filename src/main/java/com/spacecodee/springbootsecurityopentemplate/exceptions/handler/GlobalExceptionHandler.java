package com.spacecodee.springbootsecurityopentemplate.exceptions.handler;

import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiErrorDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiErrorPojo;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidCredentialsException;
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
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageUtilComponent messageUtilComponent;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorPojo> handleBusinessException(BaseException ex, HttpServletRequest request) {
        log.error("Business exception occurred: {}", ex.getMessage());
        var errorResponse = createErrorResponse(ex, request);
        return ResponseEntity.status(determineHttpStatus(ex)).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDataPojo<List<String>>> handleValidationException(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpServletRequest request) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        var errorResponse = new ApiErrorDataPojo<List<String>>();
        errorResponse.setBackendMessage(ex.getLocalizedMessage());
        errorResponse.setMessage(messageUtilComponent.getMessage("validation.error", "en"));
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setData(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorPojo> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error", ex);
        var errorResponse = createGenericErrorResponse(ex, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private @NotNull ApiErrorPojo createErrorResponse(@NotNull BaseException ex, @NotNull HttpServletRequest request) {
        var errorResponse = new ApiErrorPojo();
        String userMessage = messageUtilComponent.getMessage(ex.getMessageKey(), ex.getLocale());

        // Add technical details for backend message
        String technicalDetails = String.format(
                "Exception: %s, Key: %s, Locale: %s, Path: %s, Method: %s",
                ex.getClass().getSimpleName(),
                ex.getMessageKey(),
                ex.getLocale(),
                request.getRequestURI(),
                request.getMethod());

        errorResponse.setBackendMessage(technicalDetails);
        errorResponse.setMessage(userMessage);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());

        return errorResponse;
    }

    private @NotNull ApiErrorPojo createGenericErrorResponse(@NotNull Exception ex,
                                                             @NotNull HttpServletRequest request) {
        var errorResponse = new ApiErrorPojo();
        errorResponse.setBackendMessage(ex.getLocalizedMessage());
        errorResponse.setMessage(messageUtilComponent.getMessage("error.unexpected", "en"));
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        return errorResponse;
    }

    @Contract(pure = true)
    private HttpStatus determineHttpStatus(@NotNull BaseException ex) {
        return switch (ex) {
            case LastAdminException _, LastDeveloperException _, LastTechnicianException _ -> HttpStatus.CONFLICT;
            case InvalidCredentialsException _, TokenExpiredException _ -> HttpStatus.UNAUTHORIZED;
            case UnauthorizedException _ -> HttpStatus.FORBIDDEN;
            case UserNotFoundException _, PermissionNotFoundException _, OperationNotFoundException _,
                 ModuleNotFoundException _ -> HttpStatus.NOT_FOUND;
            case AlreadyExistsException _, PasswordDoNotMatchException _, InvalidParameterException _ ->
                    HttpStatus.BAD_REQUEST;
            case NoContentException _ -> HttpStatus.NO_CONTENT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
