package com.inkmate.app.process;

import com.inkmate.app.data.GitToken;
import com.inkmate.app.data.Problem;
import com.inkmate.app.data.ProblemExample;
import com.inkmate.app.data.Solution;
import com.inkmate.app.exception.ProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FullTextSearchQueryProcessor {

    private String FULL_TEXT_SEARCH_TITLE_BASED = "SELECT ProblemId, Title, Email, ProblemDescription, Tags, Author, DifficultyLevel FROM  Problem WHERE MATCH (Title, ProblemDescription) AGAINST (? IN NATURAL LANGUAGE MODE)";

    private String PROBLEM_SEARCH_QUERY_BY_ID = "SELECT p.ProblemId, p.Title, p.ProblemDescription, p.Tags , p.Category, p.Email, p.Author,  p.RecordCreateTime, p.RecordUpdateTime, p.DifficultyLevel ," +
            "pe.Id, pe.ProblemId, pe.Description, pe.Data, pe.Visualization, pe.Result, pe.ResultExplaination FROM  Problem p, ProblemExample pe  where p.ProblemId = pe.ProblemId and p.ProblemId = ?";

    private String SOLUTION_SEARCH_QUERY_BY_ID = "SELECT SolutionId, ProblemId, Description, Class, Langauge, Email, Author FROM  Solution where ProblemId = ?";

    private String PROBLEM_EXAMPLE_SEARCH_QUERY_BY_ID = "SELECT Id, ProblemId, Description, Data, Visualization, Result, ResultExplaination FROM ProblemExample where ProblemId = ?";

    private String GIT_TOKEN="Select * from Credentials";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource datasource;

    public List<Problem> getProblem(String title) throws ProcessingException{
        List<Problem> problems = new ArrayList<Problem>();
        try{
            PreparedStatement statement  = datasource.getConnection().prepareStatement(FULL_TEXT_SEARCH_TITLE_BASED);
            statement.setString(1, title);
            ResultSet rs  = statement.executeQuery();
            while (rs.next()) {
                Problem problem = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(rs.getString(5));
                problem.setAuthor(rs.getString(6));
                problem.setDifficultyLevel(rs.getString(7));
                problems.add(problem);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        return problems;
    }


    public Problem getProblem(long problemId) throws ProcessingException{
        Problem problem = new Problem();
        try{
            PreparedStatement statement  = datasource.getConnection().prepareStatement(PROBLEM_SEARCH_QUERY_BY_ID);
            statement.setLong(1, problemId);
            ResultSet rs  = statement.executeQuery();
            while (rs.next()) {
                //Problem person = new Problem();
                problem.setProblemId(rs.getInt(1));
                problem.setTitle(rs.getString(2));
                problem.setEmail(rs.getString(3));
                problem.setProblemDescription(rs.getString(4));
                problem.setTags(rs.getString(5));
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
        return problem;
    }

    private Set<ProblemExample> getProblemExample(long problemId) throws SQLException {
        PreparedStatement st  = datasource.getConnection().prepareStatement(PROBLEM_EXAMPLE_SEARCH_QUERY_BY_ID);
        st.setLong(1, problemId);
        ResultSet rs  = st.executeQuery();
        Set<ProblemExample> examples =  new HashSet<>();
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
        return examples;
    }


    public List<Solution> getSolution(long problemId) throws ProcessingException{
        List<Solution> solutions = new ArrayList<Solution>();
        try{
            PreparedStatement statement  = datasource.getConnection().prepareStatement(SOLUTION_SEARCH_QUERY_BY_ID);
            statement.setLong(1, problemId);
            ResultSet rs  = statement.executeQuery();
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
        return solutions;
    }

    public GitToken getGitToken() throws ProcessingException{
        GitToken gitToken = new GitToken();
        try{
            PreparedStatement statement  = datasource.getConnection().prepareStatement(GIT_TOKEN);
            ResultSet rs  = statement.executeQuery();
            while (rs.next()) {
                gitToken.setGitToken(rs.getString(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ProcessingException("Could not retrive matching titles");
        }
        return gitToken;
    }
}
