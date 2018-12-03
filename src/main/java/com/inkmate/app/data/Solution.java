package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
public class Solution {

    private long solutionId;

    private long problemId;

    private String description;

    private String className;

    private String langauge;

    private String email;

    private String author;

    private Date recordCreateTime;

    private Date recordUpdateTime;

    private SolutionReaction solutionReaction;

    private String solutionContent;
}