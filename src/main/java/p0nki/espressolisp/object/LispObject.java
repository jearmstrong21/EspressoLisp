package p0nki.espressolisp.object;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.literal.*;
import p0nki.espressolisp.object.reference.LispReference;

public abstract class LispObject {

    public abstract LispObject deepCopy() throws LispException;

    public abstract String lispStr() throws LispException;

    public abstract int lispLen() throws LispException;

    public abstract String toString();

    public abstract String getType();

    public abstract boolean isLValue();

    public final LispObject fullyDereference() throws LispException {
        LispObject obj = this;
        while (obj.isLValue()) obj = obj.get();
        return obj.deepCopy();
    }

    public abstract LispObject get();

    public final LispListLiteral asList() throws LispException {
        if (this instanceof LispListLiteral) return (LispListLiteral) this;
        throw LispException.invalidType("list", getType(), null);
    }

    public final LispMapLiteral asMap() throws LispException {
        if (this instanceof LispMapLiteral) return (LispMapLiteral) this;
        throw LispException.invalidType("map", getType(), null);
    }

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

    public final LispStringLiteral asString() throws LispException {
        if (this instanceof LispStringLiteral) return (LispStringLiteral) this;
        throw LispException.invalidType("string", getType(), null);
    }

    public final LispReference asReference() throws LispException {
        if (this instanceof LispReference) return (LispReference) this;
        throw LispException.invalidType("ref", getType(), null);
    }

}
