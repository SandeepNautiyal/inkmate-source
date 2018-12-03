package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem {

    private long problemId;

    private String title;

    private String problemDescription;

    private List<String> tags;

    private String category;

    private String email;

    private String author;

    private String difficultyLevel;

    private Date recordCreateTime;

    private Date recordUpdateTime;

    private Set<ProblemExample> examples = new HashSet<ProblemExample>();
}