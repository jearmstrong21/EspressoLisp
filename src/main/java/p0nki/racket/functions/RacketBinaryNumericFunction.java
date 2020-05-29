package p0nki.racket.functions;

import p0nki.racket.run.RacketContext;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.objects.RacketNumeric;
import p0nki.racket.objects.RacketObject;

import java.math.BigDecimal;

abstract class RacketBinaryNumericFunction extends RacketBinaryFunction {

    @Override
    protected final boolean _operatesUpon(RacketObject a, RacketObject b) {
        return a.isNumeric() && b.isNumeric();
    }

    protected abstract BigDecimal _operate(BigDecimal a, BigDecimal b) throws RacketException;

    @Override
    protected final RacketObject _operate(RacketContext context, RacketObject a, RacketObject b) throws RacketException {
        return new RacketNumeric(_operate(a.asNumeric().getDecimal(), b.asNumeric().getDecimal()));
    }

}
