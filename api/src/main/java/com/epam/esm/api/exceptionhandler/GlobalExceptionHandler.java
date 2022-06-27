package com.epam.esm.api.exceptionhandler;

import com.epam.esm.core.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final BindingResultParser bindingResultParser;

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
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40002, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidIdException(InvalidIdException exception){
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40003, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPageSizeException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidPageSizeException(InvalidPageSizeException exception){
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40005, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSortParamsException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidSortParamsException(InvalidSortParamsException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40006, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MismatchingCustomerException.class)
    public ResponseEntity<ExceptionInfo> handleMismatchingCustomerException(MismatchingCustomerException exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN, 40303, exception.getLocalizedMessage());
        return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult =  ex.getBindingResult();
        String message = "Fields of request dto has errors: " + bindingResultParser.getFieldErrMismatches(bindingResult);
        ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST, 40004, message);
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> handleLeftoverException(Exception exception) {
        ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, 50001, exception.getLocalizedMessage());
        log.error("Leftover exception", exception);
        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
