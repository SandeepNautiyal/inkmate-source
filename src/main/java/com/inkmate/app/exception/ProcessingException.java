package com.inkmate.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
public class ProcessingException extends Exception {

    public ProcessingException(String invalid_problem) {
        super(invalid_problem);
    }
}
