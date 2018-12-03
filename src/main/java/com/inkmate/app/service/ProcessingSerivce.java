package com.inkmate.app.service;

import com.inkmate.app.data.CombinedEntity;
import com.inkmate.app.data.ProblemList;
import com.inkmate.app.exception.ProcessingException;

public interface ProcessingSerivce {

    ProblemList findMatchingProblems(String title) throws ProcessingException;

    CombinedEntity findSolution(String problemTitle) throws ProcessingException;

    CombinedEntity findSolution(long id) throws ProcessingException;

    CombinedEntity findProblem(long id) throws ProcessingException;

    ProblemList findProblemByTag(String tag) throws ProcessingException;

    ProblemList findProblemByDifficultyLevel(String level) throws ProcessingException;

    ProblemList listAllProblems() throws ProcessingException;
}
