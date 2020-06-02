package p0nki.espressolisp.object.reference;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispListLiteral;
import p0nki.espressolisp.object.literal.LispMapLiteral;

import java.util.List;

public class LispListReferenceImpl implements LispReference.Impl {

    private final LispObject reference;
    private final List<LispObject> objects;
    private final int index;

    public LispListReferenceImpl(LispObject reference, List<LispObject> objects, int index) {
        this.reference = reference;
        this.objects = objects;
        this.index = index;
    }
    @Override
    public void set(LispObject newValue) throws LispException {
        if (reference.isLValue()) {
            objects.set(index, newValue);
            reference.asReference().set(new LispListLiteral(objects));
        } else {
            throw new LispException("Cannot set rvalue", null);
        }
    }

    @Override
    public LispObject get() {
        return objects.get(index);
    }

    @Override
    public void delete() throws LispException {
        if (reference.isLValue()) {
            objects.remove(index);
            reference.asReference().set(new LispListLiteral(objects));
        } else {
            throw new LispException("Cannot delete rvalue", null);
        }
    }

}
