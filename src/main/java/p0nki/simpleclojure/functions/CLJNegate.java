package p0nki.simpleclojure.functions;

import p0nki.simpleclojure.CLJContext;
import p0nki.simpleclojure.objects.CLJNumeric;
import p0nki.simpleclojure.objects.CLJObject;

public class CLJNegate extends CLJUnaryFunction {

    public static final CLJNegate INSTANCE = new CLJNegate();

    private CLJNegate() {

    }

    @Override
    protected boolean _operatesUpon(CLJObject a) {
        return a.isNumeric();
    }

    @Override
    protected CLJObject _operate(CLJContext context, CLJObject a) {
        return new CLJNumeric(a.asNumeric().getDecimal().negate());
    }

    @Override
    public String getName() {
        return "-";
    }
}
