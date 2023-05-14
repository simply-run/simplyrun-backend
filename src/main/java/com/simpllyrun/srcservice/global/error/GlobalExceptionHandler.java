package com.simpllyrun.srcservice.global.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ErrorResponse> bindException(BindException e) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        log.error(errorMap);

        return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.INPUT_VALUE_INVALID));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
