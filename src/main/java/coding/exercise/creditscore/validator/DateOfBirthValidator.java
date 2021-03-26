package coding.exercise.creditscore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, LocalDate> {

    private static final int ADULT_YEARS = 18;

    @Override
    public void initialize(DateOfBirth constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        boolean result = false;

        if (LocalDate.now().getYear() - value.getYear() >= ADULT_YEARS) {
            result = true;
        }
        return result;
    }
}
