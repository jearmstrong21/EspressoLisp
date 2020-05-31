package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

public abstract class LispTreeNode {

    private final LispToken token;

    public LispTreeNode(LispToken token) {
        this.token = token;
    }

    public abstract LispObject evaluate(LispContext context) throws LispException;

    public abstract String debugStringify(String indent);

}
