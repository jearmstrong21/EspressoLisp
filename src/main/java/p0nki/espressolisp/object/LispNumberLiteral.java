package p0nki.espressolisp.object;

public class LispNumberLiteral extends LispLiteral {

    private final double value;

    public LispNumberLiteral(double value) {
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
