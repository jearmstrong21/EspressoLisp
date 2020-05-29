package p0nki.racket.functions;

import java.math.BigDecimal;

public class RacketMultiplication extends RacketBinaryNumericFunction {

    public static final RacketMultiplication INSTANCE = new RacketMultiplication();

    private RacketMultiplication(){

    }

    @Override
    protected BigDecimal _operate(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    @Override
    public String getName() {
        return "*";
    }
}
