package io.github.wesleyosantos91.adapter.inbound.rest.v1.exception;

import io.github.wesleyosantos91.adapter.inbound.rest.v1.response.ErrorResponse;
import io.github.wesleyosantos91.adapter.inbound.rest.v1.response.ValidationErrorResponse;
import io.github.wesleyosantos91.application.core.exception.ResourceNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ValidationErrorResponse error = new ValidationErrorResponse();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception");
        error.setMessage("Validation failed");
        error.setPath(((ServletWebRequest)request).getRequest().getRequestURI());

        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage(), f.toString());
        }

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

        String errorMessage = messageSource.getMessage("resource-not-found", null, LocaleContextHolder.getLocale());

        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(errorMessage);
        error.setMessage(ExceptionUtils.getRootCauseMessage(ex));
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
