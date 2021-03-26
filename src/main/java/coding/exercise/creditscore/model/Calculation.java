package coding.exercise.creditscore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "calculations")
public class Calculation implements Serializable {

    @Id
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Client client;

    @NotNull
    @Column(name = "annual_income")
    private BigDecimal annualIncome;

    @Column(name = "credit_score")
    private long creditScore;

    public Calculation() {
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public long getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(long creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "id=" + client.getId() +
                ", annualIncome=" + annualIncome +
                ", creditScore=" + creditScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof Calculation)) {
            return false;
        }
        Calculation that = (Calculation) o;
        return client.equals(that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }
}
