package p0nki.racket.function;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;
import p0nki.racket.run.RacketContext;

import java.util.List;

public interface RacketUnaryFunctionAdapter extends RacketFunctionalInterface {

    RacketObject evaluate(RacketContext context, RacketObject arg1) throws RacketException;

    @Override
    default RacketObject evaluate(RacketContext context, List<RacketObject> args) throws RacketException {
        return evaluate(context, args.get(0));
    }

}
