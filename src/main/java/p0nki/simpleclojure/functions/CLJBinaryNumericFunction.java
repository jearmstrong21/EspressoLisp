package p0nki.simpleclojure.functions;

import p0nki.simpleclojure.CLJContext;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.objects.CLJNumeric;
import p0nki.simpleclojure.objects.CLJObject;

import java.math.BigDecimal;

abstract class CLJBinaryNumericFunction extends CLJBinaryFunction {

    @Override
    protected final boolean _operatesUpon(CLJObject a, CLJObject b) {
        return a.isNumeric() && b.isNumeric();
    }

    protected abstract BigDecimal _operate(BigDecimal a, BigDecimal b) throws ClojureException;

    @Override
    protected final CLJObject _operate(CLJContext context, CLJObject a, CLJObject b) throws ClojureException {
        return new CLJNumeric(_operate(a.asNumeric().getDecimal(), b.asNumeric().getDecimal()));
    }

}
