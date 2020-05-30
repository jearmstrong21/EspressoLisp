package p0nki.espressolisp.function;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;

import java.util.List;

public interface LispUnaryFunctionAdapter extends LispFunctionalInterface {

    LispObject evaluate(LispContext parentContext, LispObject arg1) throws LispException;

    @Override
    default LispObject evaluate(LispContext parentContext, List<LispObject> args) throws LispException {
        return evaluate(parentContext, args.get(0));
    }

}
