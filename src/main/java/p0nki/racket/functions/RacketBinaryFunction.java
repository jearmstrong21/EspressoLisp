package p0nki.racket.functions;

import p0nki.racket.run.RacketContext;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.objects.RacketObject;

import java.util.List;

abstract class RacketBinaryFunction extends RacketFunction {
    @Override
    public final boolean operatesUpon(List<RacketObject> objects) {
        if (objects.size() != 2) return false;
        return _operatesUpon(objects.get(0), objects.get(1));
    }

    protected abstract boolean _operatesUpon(RacketObject a, RacketObject b);

    protected abstract RacketObject _operate(RacketContext context, RacketObject a, RacketObject b) throws RacketException;

    @Override
    public RacketObject operate(RacketContext context, List<RacketObject> objects) throws RacketException {
        return _operate(context, objects.get(0), objects.get(1));
    }

}
