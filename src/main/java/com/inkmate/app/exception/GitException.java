package com.inkmate.app.exception;

import java.io.IOException;

public class GitException extends Exception {
    public GitException(String invalid_problem) {
        super(invalid_problem);
    }

    public GitException(String msg, IOException e) {
        super(msg, e);
    }
}
