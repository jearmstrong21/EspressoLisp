package p0nki.racket.objects;

import java.math.BigDecimal;

public final class RacketNumeric extends RacketObject {

    private final BigDecimal decimal;

    public RacketNumeric(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public BigDecimal getDecimal() {
        return new BigDecimal(decimal.toString());
    }

    @Override
    public String debugString() {
        return decimal.toString();
    }

    @Override
    public String getType() {
        return "numeric";
    }
}
