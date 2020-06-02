package p0nki.espressolisp.tree.object;

import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.tree.LispTreeNode;

public class LispReferenceNode extends LispTreeNode {

    private final String name;

    public LispReferenceNode(String name, LispToken token) {
        super(token);
        this.name = name;
    }

    @Override
    public LispObject evaluate(LispContext context) {
        return context.get(name);
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        return new JSONObject()
                .put("type", "ref")
                .put("name", name);
    }
}
