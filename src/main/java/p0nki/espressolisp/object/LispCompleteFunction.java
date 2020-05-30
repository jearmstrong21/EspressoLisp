package p0nki.espressolisp.object;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispFunctionalInterface;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.ArrayList;
import java.util.List;

public class LispCompleteFunction extends LispFunction {

    public LispCompleteFunction(String name, List<String> argNames, LispFunctionalInterface function) {
        super(name, argNames, new LispTreeNode() {
            @Override
            public LispObject evaluate(LispContext context) throws LispException {
                List<LispObject> args = new ArrayList<>();
                for (int i = 0; i < argNames.size(); i++) {
                    args.add(context.get(argNames.get(i)));
                }
                return function.evaluate(context, args);
            }

            @Override
            public String debugStringify(String indent) {
                return indent + name;
            }
        });
    }

}
