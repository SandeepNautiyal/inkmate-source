package com.inkmate.app.process;

import com.inkmate.app.data.Problem;
import com.inkmate.app.exception.ProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FullTextSearchQueryProcessor {

    private String query = "SELECT ProblemId, Title, Email, ProblemDescription, Tags, Author, DifficultyLevel FROM  Problem WHERE MATCH (Title, ProblemDescription) AGAINST (? IN NATURAL LANGUAGE MODE)";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource datasource;

    public List<Problem> getMatchingProblems(@Param("title")String title) throws ProcessingException{
        List<Problem> problems = new ArrayList<Problem>();
        try{
            PreparedStatement statement  = datasource.getConnection().prepareStatement(query);
            statement.setString(1, title);
            ResultSet rs  = statement.executeQuery();
            while (rs.next()) {
                Problem person = new Problem();
                person.setProblemId(rs.getInt(1));
                person.setTitle(rs.getString(2));
                person.setEmail(rs.getString(3));
                person.setProblemDescription(rs.getString(4));
                person.setTags(rs.getString(5));
                person.setAuthor(rs.getString(6));
                person.setDifficultyLevel(rs.getString(7));
                problems.add(person);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        return problems;
    }

}
