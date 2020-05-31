package p0nki.espressolisp.object;

import p0nki.espressolisp.exceptions.LispException;

public interface LispLValue {

    void set(LispObject newValue) throws LispException;

}
