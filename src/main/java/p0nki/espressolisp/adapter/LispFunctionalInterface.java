package p0nki.espressolisp.adapter;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;

import java.util.List;

public interface LispFunctionalInterface {

    LispObject evaluate(LispContext ctx, List<LispObject> args) throws LispException;

}
