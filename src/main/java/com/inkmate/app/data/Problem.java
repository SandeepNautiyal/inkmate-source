package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProblemId")
    private long problemId;

    @Column(name="Title")
    private String title;

    @Column(name="ProblemDescription")
    private String problemDescription;

    @Column(name="Tags")
    private String tags;

    @Column(name="Category")
    private String category;

    @Column(name="Email")
    private String email;

    @Column(name="Author")
    private String author;

    @Column(name="DifficultyLevel")
    private String difficultyLevel;

    @Column(name="RecordCreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordCreateTime;

    @Column(name="RecordUpdateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordUpdateTime;
}