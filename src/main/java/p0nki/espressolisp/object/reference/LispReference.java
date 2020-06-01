package p0nki.espressolisp.object.reference;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

public class LispReference extends LispObject {

    public interface Impl {

        void set(LispObject newValue) throws LispException;

        LispObject get() throws LispException;

        void delete() throws LispException;

    }

    private final String name;
    private boolean constant;
    private final Impl impl;

    public LispReference(String name, boolean constant, Impl impl) {
        this.name = name;
        this.constant = constant;
        this.impl = impl;
    }

    public String getName() {
        return name;
    }

    @Override
    public LispObject deepCopy() {
        return new LispReference(name, constant, impl);
    }

    public boolean isConstant() {
        return constant;
    }

    public void makeConstant() {
        constant = true;
    }

    public void delete() throws LispException {
        if (constant) throw new LispException("Cannot delete constant ref", null);
        impl.delete();
    }

    public void set(LispObject newValue) throws LispException {
        if (constant) throw new LispException("Cannot set constant ref", null);
        impl.set(newValue);
    }

    @Override
    public String lispStr() {
        return getType() + "[" + name + "]";
    }

    @Override
    public int lispLen() throws LispException {
        throw new LispException("len() is inapplicable to " + getType(), null);
    }

    @Override
    public String toString() {
        return getType() + "[" + name + "]";
    }

    @Override
    public String getType() {
        return (constant ? "const" : "") + "ref";
    }

    @Override
    public boolean isLValue() {
        return true;
    }

    @Override
    public LispObject get() throws LispException {
        return impl.get();
    }
}
