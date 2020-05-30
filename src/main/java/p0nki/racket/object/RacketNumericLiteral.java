package p0nki.racket.object;

import java.math.BigDecimal;

public class RacketNumericLiteral extends RacketObject {

    private final BigDecimal value;

    public RacketNumericLiteral(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "numeric[" + value + "]";
    }
}
