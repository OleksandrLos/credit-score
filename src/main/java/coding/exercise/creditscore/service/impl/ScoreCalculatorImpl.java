package coding.exercise.creditscore.service.impl;

import coding.exercise.creditscore.service.ScoreCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ScoreCalculatorImpl implements ScoreCalculator {

    private static final long MAX_SCORE = 999L;

    @Override
    public long calculate(int age, BigDecimal annualIncome) {
        long calculation = BigDecimal.valueOf(age)
                .add(annualIncome)
                .longValue();

        return calculation > MAX_SCORE ? MAX_SCORE : calculation;
    }

}
