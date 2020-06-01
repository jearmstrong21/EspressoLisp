package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.object.LispObject;

public final class LispNullLiteral extends LispLiteral {

    public static final LispNullLiteral INSTANCE = new LispNullLiteral();

    private LispNullLiteral() {

    }

    @Override
    public LispObject deepCopy() {
        return LispNullLiteral.INSTANCE;
    }

    @Override
    public String lispStr() {
        return "null";
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String getType() {
        return "null";
    }
}
