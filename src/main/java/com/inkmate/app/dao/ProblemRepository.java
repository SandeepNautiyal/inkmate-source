package com.inkmate.app.dao;

import com.inkmate.app.data.Problem;
import com.inkmate.app.data.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//import org.hibernate.SessionFactory;
//import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("SELECT p FROM Problem p WHERE p.title =:title")
    Problem findByTitle(@Param("title")String title);

    @Query(value = "SELECT p FROM Problem p WHERE MATCH(p.Title) AGAINST(:title IN NATURAL LANGUAGE MODE)",
            nativeQuery = true)
    Problem findByTitleSearch(@Param("title")String title);
}