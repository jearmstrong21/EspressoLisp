package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.utils.ToDebugJSON;

public abstract class LispTreeNode implements ToDebugJSON {

    private final LispToken token;

    public LispTreeNode(LispToken token) {
        this.token = token;
    }

    public LispToken getToken() {
        return token;
    }

    public abstract LispObject evaluate(LispContext context) throws LispException;

}
