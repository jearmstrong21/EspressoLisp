package p0nki.racket.functions;

import p0nki.racket.run.RacketContext;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.objects.RacketObject;

import java.util.List;

public abstract class RacketFunction {

    public abstract boolean operatesUpon(List<RacketObject> objects);

    public abstract RacketObject operate(RacketContext context, List<RacketObject> objects) throws RacketException;

    public abstract String getName();

}
