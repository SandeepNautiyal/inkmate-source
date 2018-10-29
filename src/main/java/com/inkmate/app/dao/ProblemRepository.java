package com.inkmate.app.dao;

import com.inkmate.app.data.Problem;
import com.inkmate.app.data.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//import org.hibernate.SessionFactory;
//import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("SELECT  all FROM  Problem WHERE Title = :title")
    Problem findByTitle(String title);

    @Query(value = "SELECT * FROM Problem s WHERE MATCH(s.Title) AGAINST(:title IN NATURAL LANGUAGE MODE) ",
            nativeQuery = true)
    Problem findByTitleSearch(String title);
}