package com.inkmate.app.start;

import com.inkmate.app.data.CombinedEntity;
import com.inkmate.app.data.ProblemList;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.ProcessingException;
import com.inkmate.app.service.GitService;
import com.inkmate.app.service.ProcessingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value="/api")
public class InkmateApi {
    private static Connection con = null;

    @Autowired
    private ProcessingSerivce service;

    @RequestMapping(value="/title/{title}", method = GET, produces = "application/json")
    public ResponseEntity<CombinedEntity> findSolution(@PathVariable("title") String title) {
        System.out.println("findSolution -> title="+title);
        CombinedEntity entity = null;
        try {
             entity =  service.findSolution(title);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        //System.out.println("repo ==================== "+repo.findAll());
        return new ResponseEntity<CombinedEntity>(entity, HttpStatus.OK);
    }

    @RequestMapping(value="/solution/{id}", method = GET, produces = "application/json")
    public ResponseEntity<CombinedEntity> findSolution(@PathVariable("id") long id) {
        System.out.println("findSolution -> title="+id);
        CombinedEntity entity = null;
        try {
            entity =  service.findSolution(id);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        //System.out.println("repo ==================== "+repo.findAll());
        return new ResponseEntity<CombinedEntity>(entity, HttpStatus.OK);
    }

    @RequestMapping(value="/list/{title}", method = GET, produces = "application/json")
    public ResponseEntity<ProblemList> listMatchingProblems(@PathVariable("title") String title) {
        System.out.println("listMatchingProblems -> title="+title);
        ProblemList entity = null;
        try {
            entity =  service.findMatchingProblems(title);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ProblemList>(entity, HttpStatus.OK);
    }

    @RequestMapping(value="/problem/{id}", method = GET, produces = "application/json")
    public CombinedEntity findProblem(@PathVariable("id") long id) {
        System.out.println("findProblem -> id="+id);
        CombinedEntity entity = null;
        try {
            entity = service.findProblem(id);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
        return entity;
    }
}

