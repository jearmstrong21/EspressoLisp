package p0nki.espressolisp.object;

import p0nki.espressolisp.exceptions.LispException;

public abstract class LispObject {

    public abstract String toString();

    public abstract String getType();

    public abstract boolean isLValue();

    public final LispObject fullyDereference() {
        LispObject obj = this;
        while (obj.isLValue()) obj = obj.get();
        return obj;
    }

    public abstract LispObject get();

    public final LispNumberLiteral asNumber() throws LispException {
        if (this instanceof LispNumberLiteral) return (LispNumberLiteral) this;
        throw LispException.invalidType("number", getType(), null);
    }

    public final LispBooleanLiteral asBoolean() throws LispException {
        if (this instanceof LispBooleanLiteral) return (LispBooleanLiteral) this;
        throw LispException.invalidType("boolean", getType(), null);
    }

    public final LispFunctionLiteral asFunction() throws LispException {
        if (this instanceof LispFunctionLiteral) return (LispFunctionLiteral) this;
        throw LispException.invalidType("function", getType(), null);
    }

    public final LispReference asReference() throws LispException {
        if (this instanceof LispReference) return (LispReference) this;
        throw LispException.invalidType("ref", getType(), null);
    }

}
