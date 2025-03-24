package io.linhhn.suc.core.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        StringBuilder errorMessage = new StringBuilder();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (int i = 0; i < errors.size(); i++) {
            errorMessage.append(errors.get(i).getDefaultMessage());
            if (i < errors.size() - 1) {
                errorMessage.append(", and ");
            }
        }
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 10000, errorMessage.toString(), request.getMethod() + " " + request.getRequestURI());;
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ErrorResponse> handleEntityConflictException(EntityConflictException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 10000, e.getMessage(), request.getMethod() + " " + request.getRequestURI());;
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
