package com.inkmate.app.dao;

import com.inkmate.app.data.Solution;
//import org.hibernate.SessionFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {

    @Query(value = "SELECT u, a FROM  Solution u INNER JOIN SolutionReaction a ON a.SolutionId = u.SolutionId WHERE a.SolutionId = :solutionId"
        , nativeQuery = true)
    Solution findSolution(@Param("solutionId") long solutionId);
}