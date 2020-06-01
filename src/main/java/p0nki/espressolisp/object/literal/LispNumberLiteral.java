package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.exceptions.LispException;

public class LispNumberLiteral extends LispLiteral {

    private final double value;

    public LispNumberLiteral(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String lispStr() {
        return value + "";
    }

    public LispNumberLiteral assertInteger () throws LispException {
        if((int)value != value) throw LispException.notInteger(value, null);
        return this;
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
