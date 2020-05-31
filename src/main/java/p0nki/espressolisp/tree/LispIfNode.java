package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

public class LispIfNode extends LispTreeNode {

    private final LispTreeNode condition;
    private final LispTreeNode then;
    private final LispTreeNode otherwise;

    public LispIfNode(LispTreeNode condition, LispTreeNode then, LispTreeNode otherwise, LispToken token) {
        super(token);
        this.condition = condition;
        this.then = then;
        this.otherwise = otherwise;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        if (condition.evaluate(context).fullyDereference().asBoolean().getValue()) {
            return then.evaluate(context.push());
        } else {
            return otherwise.evaluate(context.push());
        }
    }

    @Override
    public String debugStringify(String indent) {
        return "if"; // TODO stringify properly
    }
}
