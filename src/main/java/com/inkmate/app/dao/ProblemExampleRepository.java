package com.inkmate.app.dao;

import com.inkmate.app.data.ProblemExample;
import com.inkmate.app.data.SolutionReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import org.hibernate.SessionFactory;
//import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ProblemExampleRepository extends JpaRepository<ProblemExample, Long> {
}