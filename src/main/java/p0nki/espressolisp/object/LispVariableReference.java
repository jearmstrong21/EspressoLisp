package p0nki.espressolisp.object;

public class LispVariableReference extends LispObject implements LispLValue, LispRValue {

    private final String name;
    private LispObject value;

    public LispVariableReference(String name, LispObject value) {
        this.name = name;
        this.value = value;
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

    @Override
    public boolean isLValue() {
        return true;
    }

    @Override
    public boolean isRValue() {
        return true;
    }

    @Override
    public void set(LispObject newValue) {
        value = newValue.get();
    }

    @Override
    public LispObject get() {
        return value;
    }
}
