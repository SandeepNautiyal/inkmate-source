package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ProblemExample")
@Data
public class ProblemExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @ManyToOne
    private Problem problem;

    @Column(name="ProblemId")
    private long problemId;

    @Column(name="Description")
    private String description;

    @Column(name="Data")
    private String data;

    @Column(name="Visualization")
    private String visualization;

    @Column(name="Result")
    private String result;

    @Column(name="ResultExplaination")
    private String resultExplaination;
}