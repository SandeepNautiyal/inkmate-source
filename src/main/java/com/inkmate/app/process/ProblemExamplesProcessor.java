package com.inkmate.app.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import com.inkmate.app.process.InkmateUtil;

class ProblemExamplesProcessor {
    public String createTreeFromArray(int[] arr) {
        return InkmateUtil.createTreeFromArray(arr);
    }
}