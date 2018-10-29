package com.inkmate.app.data;

import lombok.Data;

import java.util.List;

@Data
public class CombinedEntity {
    private final Problem prob;

    private final List<Solution> solutions;

    public CombinedEntity(Problem prob, List<Solution> solutions) {
        this.prob = prob;
        this.solutions = solutions;
    }
}
