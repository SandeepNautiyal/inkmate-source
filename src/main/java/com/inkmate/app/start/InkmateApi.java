package com.inkmate.app.start;

import com.inkmate.app.data.CombinedEntity;
import com.inkmate.app.data.Problem;
import com.inkmate.app.data.ProblemList;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.ProcessingException;
import com.inkmate.app.service.GitService;
import com.inkmate.app.service.ProcessingSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class InkmateApi {
    private static Connection con = null;

    @Autowired
    private ProcessingSerivce service;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/title/{title}", method = GET, produces = "application/json")
    public ResponseEntity<CombinedEntity> findSolution(@PathVariable("title") String title) {
        if(log.isDebugEnabled())
            log.debug("findSolution -> title="+title);

        CombinedEntity entity = null;
        try {
             entity =  service.findSolution(title);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        if(log.isDebugEnabled())
            log.debug("findSolution <- return="+entity);

        return new ResponseEntity<CombinedEntity>(entity, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/solution/{id}", method = GET, produces = "application/json")
    public ResponseEntity<CombinedEntity> findSolution(@PathVariable("id") long id) {
        if(log.isDebugEnabled())
            log.debug("findSolution -> id="+id);

        CombinedEntity entity = null;
        try {
            entity =  service.findSolution(id);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        if(log.isDebugEnabled())
            log.debug("findSolution <- return="+entity);

        return new ResponseEntity<CombinedEntity>(entity, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/list/{title}", method = GET, produces = "application/json")
    public ResponseEntity<ProblemList> listMatchingProblems(@PathVariable("title") String title) {
        if(log.isDebugEnabled())
            log.debug("listMatchingProblems -> title="+title);

        ProblemList entity = null;
        try {
            entity =  service.findMatchingProblems(title);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        if(log.isDebugEnabled())
            log.debug("listMatchingProblems <- return="+entity);

        return new ResponseEntity<ProblemList>(entity, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/problem/{id}", method = GET, produces = "application/json")
    public CombinedEntity findProblem(@PathVariable("id") long id) {
        if(log.isDebugEnabled())
            log.debug("findProblem -> id="+id);

        CombinedEntity entity = null;
        try {
            entity = service.findProblem(id);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        if(log.isDebugEnabled())
            log.debug("findProblem <- return="+entity);

        return entity;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/problemByTag/{tag}", method = GET, produces = "application/json")
    public ProblemList findProblemByTag(@PathVariable("tag") String tag) {
        if(log.isDebugEnabled())
            log.debug("findProblemByTag -> tag="+tag);

        ProblemList entity = null;
        try {
            entity = service.findProblemByTag(tag);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        if(log.isDebugEnabled())
            log.debug("findProblemByTag <- return="+entity);

        return entity;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/problemByDifficultyLevel/{level}", method = GET, produces = "application/json")
    public ProblemList findProblemByDifficultyLevel(@PathVariable("level") String level) {
        if(log.isDebugEnabled())
            log.debug("problemByDifficultyLevel -> level="+level);

        ProblemList entity = null;
        try {
            entity = service.findProblemByDifficultyLevel(level);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        if(log.isDebugEnabled())
            log.debug("problemByDifficultyLevel <- return="+entity);

        return entity;
    }
}

