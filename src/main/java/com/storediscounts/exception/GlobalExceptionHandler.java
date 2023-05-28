package com.storediscounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> ValidationnExceptionHandler(ValidationException exception){
        HttpStatus httpStatus = exception.getHttpStatus();
        ErrorDto errorDto = new ErrorDto(httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDto,httpStatus);
    }

}
