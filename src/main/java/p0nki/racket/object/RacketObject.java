package p0nki.racket.object;

import p0nki.racket.exceptions.RacketException;

public abstract class RacketObject {

    public abstract String toString();

    public abstract String getType();

    public final RacketNumericLiteral asNumericLiteral() throws RacketException {
        if (this instanceof RacketNumericLiteral) return (RacketNumericLiteral) this;
        throw RacketException.invalidType("integer", getType(), null);
    }

}
