package p0nki.espressolisp.tree;

import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

public class LispDelNode extends LispTreeNode {

    private final String name;

    public LispDelNode(String name, LispToken token) {
        super(token);
        this.name = name;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        context.delete(name);
        return LispNullObject.INSTANCE;
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        return new JSONObject()
                .put("type", "del")
                .put("name", name);
    }
}
