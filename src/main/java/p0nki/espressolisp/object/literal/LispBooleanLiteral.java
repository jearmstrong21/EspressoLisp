package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

public class LispBooleanLiteral extends LispLiteral {

    private final boolean value;

    public LispBooleanLiteral(boolean value) {
        this.value = value;
    }

    @Override
    public LispObject deepCopy() {
        return new LispBooleanLiteral(value);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String lispStr() {
        return value + "";
    }

    @Override
    public int lispLen() throws LispException {
        throw new LispException("len() not applicable to boolean", null);
    }

    @Override
    public String toString() {
        return "boolean[" + value + "]";
    }

    @Override
    public String getType() {
        return "boolean";
    }
}
