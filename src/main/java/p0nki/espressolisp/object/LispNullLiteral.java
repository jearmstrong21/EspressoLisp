package p0nki.espressolisp.object;

public class LispNullLiteral extends LispLiteral {

    public static final LispNullLiteral INSTANCE = new LispNullLiteral();

    private LispNullLiteral() {

    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String getType() {
        return "null";
    }
}
