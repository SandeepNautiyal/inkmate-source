package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class ProblemExample {

    private long id;

    private Problem problem;

    private long problemId;

    private String description;

    private String data;

    private String visualization;

    private String result;

    private String resultExplaination;
}