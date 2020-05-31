package p0nki.espressolisp.library;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.run.LispContext;

public interface LispLibrary {

    String getName();
    void load(LispContext context) throws LispException;
    void fullImport(LispContext context) throws LispException;

}
