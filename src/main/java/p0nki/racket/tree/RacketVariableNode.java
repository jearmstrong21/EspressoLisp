package p0nki.racket.tree;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;
import p0nki.racket.run.RacketContext;

public class RacketVariableNode implements RacketTreeNode {

    private final String name;

    public RacketVariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public RacketObject evaluate(RacketContext context) throws RacketException {
        return context.get(name);
    }

    @Override
    public String toString() {
        return "variable[" + name + "]";
    }

    @Override
    public String debugStringify(String indent) {
        return indent + toString();
    }
}
