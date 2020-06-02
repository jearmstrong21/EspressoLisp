package p0nki.espressolisp.object.reference;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispMapLiteral;

import java.util.Map;

public class LispMapReferenceImpl implements LispReference.Impl {

    private final LispObject reference;
    private final Map<String, LispObject> objects;
    private final String key;

    public LispMapReferenceImpl(LispObject reference, Map<String, LispObject> objects, String key) {
        this.reference = reference;
        this.objects = objects;
        this.key = key;
    }

    @Override
    public void set(LispObject newValue) throws LispException {
        if (reference.isLValue()) {
            objects.put(key, newValue);
            reference.asReference().set(new LispMapLiteral(objects));
        } else {
            throw new LispException("Cannot set rvalue", null);
        }
    }

    @Override
    public LispObject get() {
        return objects.get(key);
    }

    @Override
    public void delete() throws LispException {
        if (reference.isLValue()) {
            objects.remove(key);
            reference.asReference().set(new LispMapLiteral(objects));
        } else {
            throw new LispException("Cannot delete rvalue", null);
        }
    }

    @Override
    public boolean canBeConstant() {
        return false;
    }

}
