package com.inkmate.app.process;

import com.inkmate.app.data.CombinedEntity;
import com.inkmate.app.data.Problem;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.GitException;
import com.inkmate.app.service.GitService;
import com.inkmate.app.service.PersistenceService;
import com.inkmate.app.service.ProcessingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import com.inkmate.app.exception.ProcessingException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestProcessor implements ProcessingSerivce {

    @Autowired
    private GitService gitService;

    @Autowired
    private PersistenceService service;

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
        Problem prob =  service.getProblem(problemId);
        if (prob != null) {
            List<Solution> solutions = service.getSolutions(prob.getProblemId());
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
}
