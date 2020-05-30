package p0nki.racket.object;

import p0nki.racket.exceptions.RacketException;

public abstract class RacketObject {

    public abstract String toString();

    public abstract String getType();

    public final RacketNumberLiteral asNumber() throws RacketException {
        if (this instanceof RacketNumberLiteral) return (RacketNumberLiteral) this;
        throw RacketException.invalidType("number", getType(), null);
    }

    public final RacketBooleanLiteral asBoolean() throws RacketException {
        if (this instanceof RacketBooleanLiteral) return (RacketBooleanLiteral) this;
        throw RacketException.invalidType("boolean", getType(), null);
    }

}
