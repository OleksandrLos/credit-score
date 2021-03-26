package coding.exercise.creditscore;

import coding.exercise.creditscore.exception.NoCreditScoreException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static coding.exercise.creditscore.controller.ClientController.getCreditScoreProperty;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
        HashMap<String, String> result = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> result.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return result;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoCreditScoreException.class)
    public Map<String, Long> handleNoCalculationsException(NoCreditScoreException exception) {
        return getCreditScoreProperty(null);
    }
}
