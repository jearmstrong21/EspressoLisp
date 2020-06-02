package p0nki.espressolisp.library;

import p0nki.espressolisp.run.LispContext;

public enum LispMathLibrary implements LispLibrary {

    INSTANCE;

    @Override
    public String getName() {
        return "math";
    }

    @Override
    public void load(LispContext context) {

    }

}
