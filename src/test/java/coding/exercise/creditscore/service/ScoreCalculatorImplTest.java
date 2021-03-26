package coding.exercise.creditscore.service;

import coding.exercise.creditscore.service.impl.ScoreCalculatorImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreCalculatorImplTest {

    ScoreCalculatorImpl cut = new ScoreCalculatorImpl();

    @Test
    void calculate() {
        long expected = 999L;
        long actual = cut.calculate(50, BigDecimal.valueOf(12300));
        assertEquals(actual, expected);

        expected = 143L;
        actual = cut.calculate(20, BigDecimal.valueOf(123));
        assertEquals(actual, expected);

        expected = 401;
        actual = cut.calculate(80, BigDecimal.valueOf(321));
        assertEquals(actual, expected);

    }
}