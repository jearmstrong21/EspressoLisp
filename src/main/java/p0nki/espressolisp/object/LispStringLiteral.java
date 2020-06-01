package p0nki.espressolisp.object;

public class LispStringLiteral extends LispLiteral {

    private final String value;

    public LispStringLiteral(String value) {
        this.value = value;
    }

    @Override
    public String lispStr() {
        return value;
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
