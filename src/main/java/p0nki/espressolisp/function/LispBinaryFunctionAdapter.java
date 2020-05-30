package p0nki.espressolisp.function;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;

import java.util.List;

public interface LispBinaryFunctionAdapter extends LispFunctionalInterface {

    LispObject evaluate(LispContext context, LispObject arg1, LispObject arg2) throws LispException;

    @Override
    default LispObject evaluate(LispContext context, List<LispObject> args) throws LispException {
        return evaluate(context, args.get(0), args.get(1));
    }

}
