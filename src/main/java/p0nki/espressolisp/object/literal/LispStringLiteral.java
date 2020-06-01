package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

public class LispStringLiteral extends LispLiteral {

    private final String value;

    public LispStringLiteral(String value) {
        this.value = value;
    }

    @Override
    public LispObject deepCopy() {
        return new LispStringLiteral(value);
    }

    @Override
    public String lispStr() {
        return value;
    }

    @Override
    public int lispLen() {
        return value.length();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "string[" + value + "]";
    }

    @Override
    public String getType() {
        return "string";
    }
}
