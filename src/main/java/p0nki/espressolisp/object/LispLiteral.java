package p0nki.espressolisp.object;

public abstract class LispLiteral extends LispObject {

    @Override
    public boolean isLValue() {
        return false;
    }

    @Override
    public boolean isRValue() {
        return true;
    }
}
