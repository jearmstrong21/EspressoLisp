package p0nki.espressolisp.object.reference;

import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispNullLiteral;

public class LispStandardReferenceImpl implements LispReference.Impl {

    private LispObject value;

    public LispStandardReferenceImpl(LispObject value) {
        this.value = value;
    }

    @Override
    public void set(LispObject newValue) {
        value = newValue;
    }

    @Override
    public LispObject get() {
        return value;
    }

    @Override
    public void delete() {
        value = LispNullLiteral.INSTANCE;
    }

    @Override
    public boolean canBeConstant() {
        return true;
    }
}
