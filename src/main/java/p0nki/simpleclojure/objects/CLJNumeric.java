package p0nki.simpleclojure.objects;

import java.math.BigDecimal;

public final class CLJNumeric extends CLJObject {

    private final BigDecimal decimal;

    public CLJNumeric(BigDecimal decimal) {
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
