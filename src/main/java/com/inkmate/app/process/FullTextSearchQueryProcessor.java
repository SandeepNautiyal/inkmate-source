package com.inkmate.app.process;

import com.inkmate.app.data.GitToken;
import com.inkmate.app.data.Problem;
import com.inkmate.app.data.ProblemExample;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
public class FullTextSearchQueryProcessor {

    private String FULL_TEXT_SEARCH_TITLE_BASED = "SELECT ProblemId, Title, Email, ProblemDescription, Tags, Author, DifficultyLevel FROM  Problem WHERE MATCH (Title, ProblemDescription) AGAINST (? IN NATURAL LANGUAGE MODE)";

    private String PROBLEM_SEARCH_QUERY_BY_ID = "SELECT p.ProblemId, p.Title, p.Email, p.ProblemDescription, p.Tags , p.Category, p.Email, p.Author,  p.RecordCreateTime, p.RecordUpdateTime, p.DifficultyLevel " +
            " FROM  Problem p where p.ProblemId = ?";

    private String PROBLEM_SEARCH_QUERY_BY_TAG = "SELECT p.ProblemId, p.Title, p.Email, p.ProblemDescription, p.Tags , p.Category, p.Email, p.Author,  p.RecordCreateTime, p.RecordUpdateTime, p.DifficultyLevel " +
            " FROM  Problem p where UPPER(p.Tags) = UPPER(?)";

    private String PROBLEM_SEARCH_QUERY_BY_LEVEl = "SELECT p.ProblemId, p.Title, p.Email, p.ProblemDescription, p.Tags , p.Category, p.Email, p.Author,  p.RecordCreateTime, p.RecordUpdateTime, p.DifficultyLevel " +
            " FROM  Problem p where UPPER(p.DifficultyLevel) = UPPER(?)";

    private String LIST_ALL_PROBLEMS = "SELECT p.ProblemId, p.Title, p.Email, p.ProblemDescription, p.Tags , p.Category, p.Email, p.Author,  p.RecordCreateTime, p.RecordUpdateTime, p.DifficultyLevel " +
            " FROM  Problem p";

    private String SOLUTION_SEARCH_QUERY_BY_ID = "SELECT SolutionId, ProblemId, Description, Class, Langauge, Email, Author FROM  Solution where ProblemId = ?";

    private String PROBLEM_EXAMPLE_SEARCH_QUERY_BY_ID = "SELECT Id, ProblemId, Description, Data, Visualization, Result, ResultExplaination FROM ProblemExample where ProblemId = ?";

    private String GIT_TOKEN="Select * from Credentials";

    @Autowired
    private DataSource datasource;

    private GitToken gitToken = null;

    public List<Problem> getProblem(String title) throws ProcessingException{
        if(log.isDebugEnabled())
            log.debug("getProblem -> title="+title);

        List<Problem> problems = new ArrayList<Problem>();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        try{
            con  = datasource.getConnection();
            stmt  = con.prepareStatement(FULL_TEXT_SEARCH_TITLE_BASED);
            stmt.setString(1, title);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                Problem problem = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(Arrays.asList(rs.getString(5).split(",")));
                problem.setAuthor(rs.getString(6));
                problem.setDifficultyLevel(rs.getString(7));
                problems.add(problem);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(log.isDebugEnabled())
            log.debug("getProblem <- return="+problems);
        return problems;
    }


    public Problem getProblem(long problemId) throws ProcessingException{
        if(log.isDebugEnabled())
            log.debug("getProblem -> problemId="+problemId);

        Problem problem = new Problem();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        try{
            con = datasource.getConnection();
            stmt  = con.prepareStatement(PROBLEM_SEARCH_QUERY_BY_ID);
            stmt.setLong(1, problemId);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                //Problem person = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(Arrays.asList(rs.getString(5).split(",")));
                problem.setAuthor(rs.getString(6));
                problem.setDifficultyLevel(rs.getString(7));
            }

            Set<ProblemExample> examples = getProblemExample(problemId);
            problem.setExamples(examples);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(log.isDebugEnabled())
            log.debug("getProblem <- return="+problem);
        return problem;
    }

    private Set<ProblemExample> getProblemExample(long problemId) {
        if(log.isDebugEnabled())
            log.debug("getProblemExample -> problemId="+problemId);
        Set<ProblemExample> examples =  new HashSet<>();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        try{
            con = datasource.getConnection();
            stmt  = con.prepareStatement(PROBLEM_EXAMPLE_SEARCH_QUERY_BY_ID);
            stmt.setLong(1, problemId);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                ProblemExample example = new ProblemExample();
                example.setId(rs.getLong(1));
                example.setProblemId(rs.getLong(2));
                example.setDescription(rs.getString(3));
                example.setData(rs.getString(4));
                example.setVisualization(rs.getString(5));
                example.setResult(rs.getString(6));
                example.setResultExplaination(rs.getString(7));
                examples.add(example);
            }
        }
        catch (SQLException e){

        }
        finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(log.isDebugEnabled())
            log.debug("getSolution <- return="+examples);

        return examples;
    }


    public List<Solution> getSolution(long problemId) throws ProcessingException{
        if(log.isDebugEnabled())
            log.debug("getSolution -> problemId="+problemId);

        List<Solution> solutions = new ArrayList<Solution>();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        try{
            con = datasource.getConnection();
            stmt  = con.prepareStatement(SOLUTION_SEARCH_QUERY_BY_ID);
            stmt.setLong(1, problemId);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                Solution solution = new Solution();
                solution.setSolutionId(rs.getInt(1));
                solution.setProblemId(rs.getInt(2));
                solution.setDescription(rs.getString(3));
                solution.setClassName(rs.getString(4));
                solution.setLangauge(rs.getString(5));
                solution.setEmail(rs.getString(6));
                solution.setAuthor(rs.getString(7));
                solutions.add(solution);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(log.isDebugEnabled())
            log.debug("getSolution <- return="+solutions);

        return solutions;
    }

    public GitToken getGitToken() throws ProcessingException{
        if(log.isDebugEnabled())
            log.debug("getGitToken -> ");

        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        if(gitToken != null){
            return gitToken;
        }

        try{
            con = datasource.getConnection();
            stmt  = con.prepareStatement(GIT_TOKEN);
            rs  = stmt.executeQuery();
            gitToken = new GitToken();
            while (rs.next()) {
                gitToken.setGitToken(rs.getString(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(log.isDebugEnabled())
            log.debug("getGitToken <- return="+gitToken);

        return gitToken;
    }

    public List<Problem> findProblemByTag(String tag) throws ProcessingException {
        List<Problem> problems = new ArrayList<Problem>();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        try{
            con  = datasource.getConnection();
            stmt  = con.prepareStatement(PROBLEM_SEARCH_QUERY_BY_TAG);
            stmt.setString(1, tag);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                Problem problem = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(Arrays.asList(rs.getString(5).split(",")));
                problem.setAuthor(rs.getString(6));
                problem.setDifficultyLevel(rs.getString(7));
                problems.add(problem);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return problems;
    }

    public List<Problem> findProblemByDifficultyLevel(String level) throws ProcessingException{
        List<Problem> problems = new ArrayList<Problem>();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs =null;
        try{
            con  = datasource.getConnection();
            stmt  = con.prepareStatement(PROBLEM_SEARCH_QUERY_BY_LEVEl);
            stmt.setString(1, level);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                Problem problem = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(Arrays.asList(rs.getString(5).split(",")));
                problem.setAuthor(rs.getString(6));
                problem.setDifficultyLevel(rs.getString(7));
                problems.add(problem);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return problems;
    }

    public List<Problem> listAllProblems() throws ProcessingException {
        List<Problem> problems = new ArrayList<Problem>();
        Connection con = null;
        PreparedStatement stmt  = null;
        ResultSet rs = null;
        try{
            con  = datasource.getConnection();
            stmt  = con.prepareStatement(LIST_ALL_PROBLEMS);
            rs  = stmt.executeQuery();
            while (rs.next()) {
                Problem problem = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(Arrays.asList(rs.getString(5).split(",")));
                problem.setAuthor(rs.getString(6));
                problem.setDifficultyLevel(rs.getString(7));
                problems.add(problem);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return problems;
    }
}
