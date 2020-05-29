package p0nki.simpleclojure.functions;

import java.math.BigDecimal;

public class CLJAddition extends CLJBinaryNumericFunction {

    public static final CLJAddition INSTANCE = new CLJAddition();

    private CLJAddition(){

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
