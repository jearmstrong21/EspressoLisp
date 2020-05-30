package p0nki.racket.function;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;
import p0nki.racket.run.RacketContext;

import java.util.List;

public interface RacketBinaryFunctionAdapter extends RacketFunctionalInterface {

    RacketObject evaluate(RacketContext context, RacketObject arg1, RacketObject arg2) throws RacketException;

    @Override
    default RacketObject evaluate(RacketContext context, List<RacketObject> args) throws RacketException {
        return evaluate(context, args.get(0), args.get(1));
    }

}
