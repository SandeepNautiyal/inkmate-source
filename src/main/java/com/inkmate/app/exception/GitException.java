package com.inkmate.app.exception;

public class GitException extends Exception {
    public GitException(String invalid_problem) {
        super(invalid_problem);
    }
}
