package p0nki.simpleclojure.functions;

import java.math.BigDecimal;

public class CLJMultiplication extends CLJBinaryNumericFunction {

    public static final CLJMultiplication INSTANCE = new CLJMultiplication();

    private CLJMultiplication(){

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
