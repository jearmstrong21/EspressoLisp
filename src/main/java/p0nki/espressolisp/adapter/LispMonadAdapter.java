package p0nki.espressolisp.adapter;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;

import java.util.List;

public interface LispMonadAdapter extends LispFunctionalInterface {

    LispObject evaluate(LispContext ctx, LispObject arg1) throws LispException;

    @Override
    default LispObject evaluate(LispContext ctx, List<LispObject> args) throws LispException {
        if (args.size() != 1) throw LispException.invalidArgList(1, args.size(), null);
        return evaluate(ctx, args.get(0));
    }

}
