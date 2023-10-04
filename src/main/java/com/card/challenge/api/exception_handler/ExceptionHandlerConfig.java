package com.card.challenge.api.exception_handler;

import com.card.challenge.domain.utils.Message;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.OffsetDateTime.now;

@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = getProblem(status, Message.toLocale("Global.Error.SomethingUnexpected"));
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = getProblem(status, ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(IllegalValueException.class)
    public ResponseEntity<Object> handleIllegalValueException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = getProblem(status, ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRunTimeException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = getProblem(status, ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem getProblem(HttpStatus status, String detail) {
        return Problem.builder().timestamp(now()).status(status.value()).detail(detail).build();
    }
}
