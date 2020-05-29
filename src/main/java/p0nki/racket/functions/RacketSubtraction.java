package p0nki.racket.functions;

import java.math.BigDecimal;

public class RacketSubtraction extends RacketBinaryNumericFunction {

    public static final RacketSubtraction INSTANCE = new RacketSubtraction();

    private RacketSubtraction(){

    }

    @Override
    protected BigDecimal _operate(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    public String getName() {
        return "-";
    }
}
