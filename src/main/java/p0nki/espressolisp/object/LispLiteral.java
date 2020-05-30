package p0nki.espressolisp.object;

public abstract class LispLiteral extends LispObject implements LispRValue {

    @Override
    public LispObject get() {
        return this;
    }

    @Override
    public boolean isLValue() {
        return false;
    }

    @Override
    public boolean isRValue() {
        return true;
    }
}
