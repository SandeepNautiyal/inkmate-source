package com.inkmate.app.process;

import com.inkmate.app.data.*;
import com.inkmate.app.exception.GitException;
import com.inkmate.app.service.GitService;
import com.inkmate.app.service.PersistenceService;
import com.inkmate.app.service.ProcessingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import com.inkmate.app.exception.ProcessingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestProcessor implements ProcessingSerivce {

    @Autowired
    private GitService gitService;

    @Autowired
    private PersistenceService service;

    @Autowired
    private FullTextSearchQueryProcessor ftsProcessor;

    @Override
    public ProblemList findMatchingProblems(String title) throws ProcessingException{
        List<Problem> problems = ftsProcessor.getProblem(title);
        List<Problem> result = new ArrayList<>();
        for(Problem prob : problems){
            System.out.println("problem id  ========================== "+prob.getProblemId()+" title  = "+prob.getTitle());

//            List<Solution> solutions = service.getSolutions(prob.getProblemId());
//            System.out.println("problem result =========  ==================solutions======== "+solutions);

            Problem problem = ftsProcessor.getProblem(prob.getProblemId());
            //Problem prob1  = service.getProblem(prob.getProblemId());
            System.out.println("problem result =========  ========================== "+problem);

            result.add(problem);
        }
        return new ProblemList(result);
    }

    @Override
    public CombinedEntity findSolution(String title) throws ProcessingException{
        Problem prob =  service.getProblemByTitle(title);
        if(prob != null){
            List<Solution> solutions  = service.getSolutions(prob.getProblemId());
            try{
                for(Solution s : solutions){
                    String content = gitService.readFromGit(s.getClassName());
                    s.setSolutionContent(content);
                }
            }
            catch(GitException e){
                throw new ProcessingException("Unable to process request at this time");
            }
            return new CombinedEntity(prob, solutions);
        }
        else {
            throw new ProcessingException("Invalid Problem");
        }
    }

    @Override
    public CombinedEntity findSolution(long problemId) throws ProcessingException {
        Problem prob =  ftsProcessor.getProblem(problemId);
        if (prob != null) {
            List<Solution> solutions = ftsProcessor.getSolution(prob.getProblemId());
            try {
                for (Solution s : solutions) {
                    String content = gitService.readFromGit(s.getClassName());
                    s.setSolutionContent(content);
                }
            } catch (GitException e) {
                throw new ProcessingException("Unable to process request at this time");
            }
            return new CombinedEntity(prob, solutions);
        } else {
            throw new ProcessingException("Invalid Problem");
        }
    }

    @Override
    public CombinedEntity findProblem(long id) throws ProcessingException {
        return new CombinedEntity(ftsProcessor.getProblem(id), null);
    }

    @Override
    public ProblemList findProblemByTag(String tag) throws ProcessingException {
        return new ProblemList(ftsProcessor.findProblemByTag(tag));
    }

    @Override
    public ProblemList findProblemByDifficultyLevel(String level) throws ProcessingException {
        return new ProblemList(ftsProcessor.findProblemByDifficultyLevel(level));
    }
}
