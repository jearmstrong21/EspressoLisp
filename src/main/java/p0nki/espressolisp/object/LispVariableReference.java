package p0nki.espressolisp.object;

import p0nki.espressolisp.exceptions.LispException;

public class LispVariableReference extends LispObject implements LispLValue, LispRValue {

    private final String name;
    private boolean constant;
    private LispObject value;

    public LispVariableReference(String name, boolean constant, LispObject value) {
        this.name = name;
        this.constant = constant;
        this.value = value;
    }

    public boolean isConstant() {
        return constant;
    }

    public void makeConstant() {
        constant = true;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (constant ? "const" : "") + "ref[" + name + "]";
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
    public void set(LispObject newValue) throws LispException {
        if (constant) throw LispException.unableToAssignToConstant(name, null);
        value = newValue.get();
    }

    @Override
    public LispObject get() {
        return value;
    }
}
