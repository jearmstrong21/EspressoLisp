package p0nki.espressolisp.object;

public class LispVariableReference extends LispObject {

    private final String name;

    public LispVariableReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ref[" + name + "]";
    }

    @Override
    public String getType() {
        return "ref";
    }
}
