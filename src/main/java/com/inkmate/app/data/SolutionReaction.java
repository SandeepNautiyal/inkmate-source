package com.inkmate.app.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class SolutionReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SolutionId")
    private long solutionId;

    @Column(name="Likes")
    private long Likes;

    @Column(name="Dislikes")
    private String dislikes;
}