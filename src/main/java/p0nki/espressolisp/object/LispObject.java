package p0nki.espressolisp.object;

import p0nki.espressolisp.exceptions.LispException;

public abstract class LispObject {

    public abstract String toString();

    public abstract String getType();

    public abstract boolean isLValue();
    public abstract boolean isRValue();

    public final LispNumberLiteral asNumber() throws LispException {
        if (this instanceof LispNumberLiteral) return (LispNumberLiteral) this;
        throw LispException.invalidType("number", getType(), null);
    }

    public final LispBooleanLiteral asBoolean() throws LispException {
        if (this instanceof LispBooleanLiteral) return (LispBooleanLiteral) this;
        throw LispException.invalidType("boolean", getType(), null);
    }

}
