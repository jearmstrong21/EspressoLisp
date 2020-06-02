package p0nki.espressolisp.tree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispMapLiteral;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LispMapNode extends LispTreeNode {

    private final List<LispTreeNode> keys;
    private final List<LispTreeNode> values;

    public LispMapNode(List<LispTreeNode> keys, List<LispTreeNode> values, LispToken token) throws LispException {
        super(token);
        if (keys.size() != values.size()) throw new LispException("Keys must be same length as values", null);
        this.keys = keys;
        this.values = values;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        Map<String, LispObject> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i).evaluate(context).fullyDereference().asString().getValue(), values.get(i).evaluate(context).fullyDereference());
        }
        return new LispMapLiteral(map);
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        JSONArray arrKeys = new JSONArray();
        JSONArray arrValues = new JSONArray();
        for (int i = 0; i < keys.size(); i++) {
            arrKeys.put(keys.get(i).toDebugJSON());
            arrValues.put(values.get(i).toDebugJSON());
        }
        return new JSONObject()
                .put("type", "uninvoked_map")
                .put("keys", arrKeys)
                .put("values", arrValues);
    }
}
