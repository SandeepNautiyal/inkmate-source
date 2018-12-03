package com.inkmate.app.dao;

import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.PersistenceException;
import com.inkmate.app.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inkmate.app.data.Problem;

import java.util.Arrays;
import java.util.List;

@Service
public class PersistenceManager implements PersistenceService {
    @Override
    public List<Solution> getSolutions(long problemId) {
        return null;
    }

    @Override
    public Problem getProblem(long problemId) {
        return null;
    }

    @Override
    public Problem getProblemByTitle(String title) {
        return null;
    }

//    @Override
//    public List<Solution> getSolutions(long problemId) {
//        return solutionRepo.findAllById(Arrays.asList(problemId));
//    }
//
//    @Override
//    public Problem getProblemByTitle(String title){
//        return  problemRepo.findByTitle(title);
//    }
//
//    @Override
//    public Problem getProblem(long id) {
//        return problemRepo.findById(id);
//    }

}
