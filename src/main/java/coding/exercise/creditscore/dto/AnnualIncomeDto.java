package coding.exercise.creditscore.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AnnualIncomeDto {

    @NotNull
    @Min(1)
    private BigDecimal annualIncome;

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }
}
