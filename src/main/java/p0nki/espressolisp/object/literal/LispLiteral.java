package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.object.LispObject;

public abstract class LispLiteral extends LispObject {

    @Override
    public LispObject get() {
        return this;
    }

    @Override
    public boolean isLValue() {
        return false;
    }

}
