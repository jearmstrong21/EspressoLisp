package p0nki.espressolisp.tree;

import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

public class LispLiteralNode extends LispTreeNode {

    private final LispObject value;

    public LispLiteralNode(LispObject value, LispToken token) {
        super(token);
        this.value = value;
    }

    public LispObject getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "literal[" + value + "]";
    }

    @Override
    public LispObject evaluate(LispContext context) {
        return value;
    }

    @Override
    public String debugStringify(String indent) {
        return indent + toString();
    }
}
