package p0nki.espressolisp.utils;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

public class LispStandardLogger implements LispLogger {
    @Override
    public void out(LispObject object) throws LispException {
        System.out.print("=> " + object);
        while (object.isLValue()) {
            object = object.get();
            System.out.print(" = " + object);
        }
        System.out.println();
    }

    @Override
    public void out(String message) {
        System.out.println("=> " + message);
    }

    @Override
    public void warn(LispObject object) throws LispException {
        System.err.print("=> " + object);
        while (object.isLValue()) {
            object = object.get();
            System.err.print(" = " + object);
        }
        System.err.println();
    }

    @Override
    public void warn(String message) {
        System.err.println("=> " + message);
    }
}
