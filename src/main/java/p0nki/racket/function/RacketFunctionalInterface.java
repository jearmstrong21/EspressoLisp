package p0nki.racket.function;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;

import java.util.List;

public interface RacketFunctionalInterface {

    RacketObject evaluate(List<RacketObject> args) throws RacketException;

}
