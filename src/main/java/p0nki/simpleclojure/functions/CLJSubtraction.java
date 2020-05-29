package p0nki.simpleclojure.functions;

import java.math.BigDecimal;

public class CLJSubtraction extends CLJBinaryNumericFunction {

    public static final CLJSubtraction INSTANCE = new CLJSubtraction();

    private CLJSubtraction(){

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
