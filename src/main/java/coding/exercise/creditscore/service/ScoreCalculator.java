package coding.exercise.creditscore.service;

import java.math.BigDecimal;

public interface ScoreCalculator {

    long calculate(int age, BigDecimal annualIncome);
}
