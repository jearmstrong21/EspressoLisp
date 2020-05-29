package p0nki.racket.functions;

import p0nki.racket.exceptions.RacketException;

import java.math.BigDecimal;

public class RacketDivision extends RacketBinaryNumericFunction {

    public static final RacketDivision INSTANCE = new RacketDivision();

    private RacketDivision() {

    }

    @Override
    protected BigDecimal _operate(BigDecimal a, BigDecimal b) throws RacketException {
        if(b.equals(BigDecimal.ZERO))throw new RacketException("No division by zero");
        return a.divide(b);
    }

    @Override
    public String getName() {
        return "/";
    }
}
