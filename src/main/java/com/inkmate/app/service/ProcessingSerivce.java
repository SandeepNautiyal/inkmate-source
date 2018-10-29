package com.inkmate.app.service;

import com.inkmate.app.data.CombinedEntity;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.ProcessingException;

import java.util.List;

public interface ProcessingSerivce {
    CombinedEntity findSolution(String problemTitle) throws ProcessingException;

    CombinedEntity findSolution(long id) throws ProcessingException;
}
