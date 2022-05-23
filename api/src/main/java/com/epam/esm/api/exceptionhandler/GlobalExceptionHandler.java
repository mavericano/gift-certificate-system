package com.epam.esm.api.exceptionhandler;

import com.epam.esm.core.exception.DuplicateTagNameException;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final BindingResultParser bindingResultParser;

    public GlobalExceptionHandler(BindingResultParser bindingResultParser) {
        this.bindingResultParser = bindingResultParser;
    }

    @ExceptionHandler(NoSuchRecordException.class)
    public ResponseEntity<ExceptionInfo> handleNoSuchRecordException(NoSuchRecordException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND, 40401, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateTagNameException.class)
    public ResponseEntity<ExceptionInfo> handleDuplicateTagNameException(DuplicateTagNameException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT, 40901, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRecordException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidRecordException(InvalidRecordException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40001, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ExceptionInfo> handleJsonMappingException(JsonMappingException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40001, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidIdException(InvalidIdException exception){
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40001, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult =  ex.getBindingResult();
        String message = "Fields of request dto has errors: " + bindingResultParser.getFieldErrMismatches(bindingResult);
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40001, message);
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionInfo> handleLeftoverException(Exception exception) {
//        ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, 50001, exception.getLocalizedMessage());
//        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
