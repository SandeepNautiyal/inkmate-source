package com.inkmate.app.service;

import com.inkmate.app.data.Problem;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.PersistenceException;

import java.util.List;

public interface PersistenceService {
    public List<Solution> getSolutions(long problemId);

    public Problem getProblem(long title);

    public Problem getProblemByTitle(String title);
}
