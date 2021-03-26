package coding.exercise.creditscore.validator;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateOfBirthValidatorTest {

    private DateOfBirthValidator cut = new DateOfBirthValidator();

    @Test
    void shouldReturnFalse() {
        assertFalse(cut.isValid(LocalDate.now(), null));
        assertFalse(cut.isValid(LocalDate.now().plus(1, ChronoUnit.YEARS), null));
        assertFalse(cut.isValid(LocalDate.now().minus(10, ChronoUnit.YEARS), null));
    }

    @Test
    void shouldReturnTrue() {
        assertTrue(cut.isValid(LocalDate.now().minus(19, ChronoUnit.YEARS), null));
    }
}