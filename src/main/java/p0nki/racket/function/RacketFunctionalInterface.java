package p0nki.racket.function;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;
import p0nki.racket.run.RacketContext;

import java.util.List;

public interface RacketFunctionalInterface {

    RacketObject evaluate(RacketContext context, List<RacketObject> args) throws RacketException;

}
