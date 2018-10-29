package com.inkmate.app.exception;

public class PersistenceException extends Exception {
    public PersistenceException(String invalid_problem) {
        super(invalid_problem);
    }
}
