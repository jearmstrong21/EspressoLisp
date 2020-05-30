package p0nki.racket.object;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.function.RacketFunctionalInterface;
import p0nki.racket.run.RacketContext;
import p0nki.racket.tree.RacketTreeNode;

import java.util.ArrayList;
import java.util.List;

public class RacketCompleteFunction extends RacketFunction {

    public RacketCompleteFunction(String name, List<String> argNames, RacketFunctionalInterface function) {
        super(name, argNames, new RacketTreeNode() {
            @Override
            public RacketObject evaluate(RacketContext context) throws RacketException {
                List<RacketObject> args = new ArrayList<>();
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
