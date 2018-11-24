package com.inkmate.app.dao;

import com.inkmate.app.data.Solution;
import com.inkmate.app.data.SolutionReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import org.hibernate.SessionFactory;
//import org.springframework.data.jpa.repository.JpaRepository;


public interface SolutionReactionRepository extends JpaRepository<SolutionReaction, Long> {
}