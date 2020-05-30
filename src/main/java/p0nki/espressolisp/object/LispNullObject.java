package p0nki.espressolisp.object;

public class LispNullObject extends LispObject {

    public static final LispNullObject INSTANCE = new LispNullObject();

    private LispNullObject() {

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
