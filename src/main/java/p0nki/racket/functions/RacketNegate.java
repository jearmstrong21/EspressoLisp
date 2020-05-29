package p0nki.racket.functions;

import p0nki.racket.run.RacketContext;
import p0nki.racket.objects.RacketNumeric;
import p0nki.racket.objects.RacketObject;

public class RacketNegate extends RacketUnaryFunction {

    public static final RacketNegate INSTANCE = new RacketNegate();

    private RacketNegate() {

    }

    @Override
    protected boolean _operatesUpon(RacketObject a) {
        return a.isNumeric();
    }

    @Override
    protected RacketObject _operate(RacketContext context, RacketObject a) {
        return new RacketNumeric(a.asNumeric().getDecimal().negate());
    }

    @Override
    public String getName() {
        return "-";
    }
}
