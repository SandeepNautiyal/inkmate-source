package com.inkmate.app.start;

import com.inkmate.app.data.CombinedEntity;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.ProcessingException;
import com.inkmate.app.reponse.InkmateResponse;
import com.inkmate.app.service.GitService;
import com.inkmate.app.service.ProcessingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value="/findSolution/{title}", method = GET, produces = "application/json")
    public ResponseEntity<InkmateResponse> findSolution(@PathVariable("title") String title) {
        System.out.println("findSolution -> title="+title);
        try {
            CombinedEntity entity =  service.findSolution(title);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        //System.out.println("repo ==================== "+repo.findAll());
        return null;
    }

    @RequestMapping(value="/findSolution/{id}", method = GET, produces = "application/json")
    public ResponseEntity<InkmateResponse> findSolution(@PathVariable("id") long id) {
        System.out.println("findSolution -> title="+id);

        //System.out.println("repo ==================== "+repo.findAll());
        return null;
    }
}

