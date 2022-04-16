package com.epam.esm.api.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionInfo {
    HttpStatus httpStatus;
    int errorCode;
    String errorMessage;
}
