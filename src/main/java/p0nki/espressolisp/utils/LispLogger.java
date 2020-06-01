package p0nki.espressolisp.utils;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

public interface LispLogger {

    void out(LispObject object) throws LispException;
    void out(String message);

    void warn(LispObject object) throws LispException;
    void warn(String message);

}
