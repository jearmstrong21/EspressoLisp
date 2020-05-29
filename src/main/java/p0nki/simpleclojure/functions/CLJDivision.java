package p0nki.simpleclojure.functions;

import p0nki.simpleclojure.exceptions.ClojureException;

import java.math.BigDecimal;

public class CLJDivision extends CLJBinaryNumericFunction {

    public static final CLJDivision INSTANCE = new CLJDivision();

    private CLJDivision() {

    }

    @Override
    protected BigDecimal _operate(BigDecimal a, BigDecimal b) throws ClojureException {
        if(b.equals(BigDecimal.ZERO))throw new ClojureException("No division by zero");
        return a.divide(b);
    }

    @Override
    public String getName() {
        return "/";
    }
}
