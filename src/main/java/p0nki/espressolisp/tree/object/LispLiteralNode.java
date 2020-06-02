package p0nki.espressolisp.tree.object;

import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.tree.LispTreeNode;
import p0nki.espressolisp.utils.ToDebugJSON;

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
    public LispObject evaluate(LispContext context) {
        return value;
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        return new JSONObject()
                .put("type", "literal")
                .put("value", value instanceof ToDebugJSON ? ((ToDebugJSON) value).toDebugJSON() : value);
    }
}
