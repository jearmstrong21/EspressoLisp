package p0nki.espressolisp.utils;

import p0nki.espressolisp.object.LispObject;

public interface LispLogger {

    void out(LispObject object);

    void out(String message);

    void warn(LispObject object);

    void warn(String message);

}
