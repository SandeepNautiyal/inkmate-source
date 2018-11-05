package com.inkmate.app.data;

import lombok.Data;

import java.util.List;

@Data
public class ProblemList {

    private final List<Problem> prob;

    public ProblemList(List<Problem> problems) {
        this.prob = problems;
    }
}
