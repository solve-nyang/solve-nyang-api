package com.ssafy.solvedpick.avatars.dto;

import com.ssafy.solvedpick.avatars.domain.Grade;
import lombok.Getter;

@Getter
public class GradeStatistics {
    private final int grade;
    private final long count;
    private final double probability;
    private final double individualProbability;

    public GradeStatistics(int grade, long count, double probability, double individualProbability)  {
        this.grade = grade;
        this.count = count;
        this.probability = probability;
        this.individualProbability = individualProbability;
    }
}
