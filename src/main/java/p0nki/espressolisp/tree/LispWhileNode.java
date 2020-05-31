package p0nki.espressolisp.tree;

import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

public class LispWhileNode extends LispTreeNode {

    private final LispTreeNode condition;
    private final LispTreeNode body;

    public LispWhileNode(LispTreeNode condition, LispTreeNode body, LispToken token) {
        super(token);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        while (condition.evaluate(context).asBoolean().getValue()) {
            body.evaluate(context.push());
        }
        return LispNullObject.INSTANCE;
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        return new JSONObject()
                .put("type", "while")
                .put("condition", condition.toDebugJSON())
                .put("body", body.toDebugJSON());
    }
}
