package p0nki.racket.object;

public class RacketNumberLiteral extends RacketObject {

    private final double value;

    public RacketNumberLiteral(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "number[" + value + "]";
    }

    @Override
    public String getType() {
        return "number";
    }
}
