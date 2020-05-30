package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;

public class LispVariableNode implements LispTreeNode {

    private final String name;

    public LispVariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
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
