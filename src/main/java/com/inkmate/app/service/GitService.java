package com.inkmate.app.service;

import com.inkmate.app.exception.GitException;

import java.io.IOException;

public interface GitService {
    void readFromGit() throws IOException;

    String readFromGit(String fileName) throws GitException;
}
