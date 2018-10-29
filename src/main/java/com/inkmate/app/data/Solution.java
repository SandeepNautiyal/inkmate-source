package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="Solution")
@Data
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SolutionId")
    private long solutionId;

    @Column(name="ProblemId")
    private long problemId;

    @Column(name="Description")
    private String description;

    @Column(name="Class")
    private String className;

    @Column(name="Language")
    private String langauge;

    @Column(name="Email")
    private String email;

    @Column(name="Author")
    private String author;

    @Column(name="RecordCreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordCreateTime;

    @Column(name="RecordUpdateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordUpdateTime;

    @OneToOne()
    @JoinColumn(name="SolutionId")
    private SolutionReaction solutionReaction;

    private String solutionContent;
}