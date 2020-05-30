package p0nki.racket.object;

public class RacketBooleanLiteral extends RacketObject {

    private final boolean value;

    public RacketBooleanLiteral(boolean value) {
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
