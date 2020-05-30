package p0nki.racket.function;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;

import java.util.List;

public class RacketBinaryFunctionAdapter implements RacketFunctionalInterface {

    

    @Override
    public RacketObject evaluate(List<RacketObject> args) throws RacketException {
        return evaluate(args.get(0), args.get(1));
    }

}
