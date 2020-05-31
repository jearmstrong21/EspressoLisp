package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

public class LispForNode extends LispTreeNode {

    private final LispTreeNode initialization;
    private final LispTreeNode condition;
    private final LispTreeNode increment;
    private final LispTreeNode body;

    public LispForNode(LispTreeNode initialization, LispTreeNode condition, LispTreeNode increment, LispTreeNode body, LispToken token) {
        super(token);
        this.initialization = initialization;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        initialization.evaluate(context);
        while (condition.evaluate(context).fullyDereference().asBoolean().getValue()) {
            body.evaluate(context.push());
            increment.evaluate(context);
        }
        return LispNullObject.INSTANCE;
    }

    @Override
    public String debugStringify(String indent) {
        return "for"; // TODO actually stringify everything probably
    }
}
