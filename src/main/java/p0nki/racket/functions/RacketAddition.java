package p0nki.racket.functions;

import java.math.BigDecimal;

public class RacketAddition extends RacketBinaryNumericFunction {

    public static final RacketAddition INSTANCE = new RacketAddition();

    private RacketAddition(){

    }

    @Override
    protected BigDecimal _operate(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    public String getName() {
        return "+";
    }
}
