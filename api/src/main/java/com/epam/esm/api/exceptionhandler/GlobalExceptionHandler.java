package com.epam.esm.api.exceptionhandler;

import com.epam.esm.core.exception.DuplicateTagNameException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchRecordException.class)
    public ResponseEntity<ExceptionInfo> handleNoSuchRecordException(NoSuchRecordException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND, 40401, exception.getMessage());
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateTagNameException.class)
    public ResponseEntity<ExceptionInfo> handleDuplicateTagNameException(DuplicateTagNameException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT, 40901, exception.getMessage());
        return new ResponseEntity<>(info, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRecordException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidRecordException(InvalidRecordException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40001, exception.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ExceptionInfo> handleJsonMappingException(JsonMappingException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40001, exception.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionInfo> handleLeftoverException(Exception exception) {
//        ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, 50001, exception.getMessage());
//        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
