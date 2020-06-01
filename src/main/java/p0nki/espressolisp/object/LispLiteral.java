package p0nki.espressolisp.object;

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
