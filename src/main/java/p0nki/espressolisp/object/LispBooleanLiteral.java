package p0nki.espressolisp.object;

public class LispBooleanLiteral extends LispLiteral {

    private final boolean value;

    public LispBooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
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
