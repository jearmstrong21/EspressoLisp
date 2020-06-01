package p0nki.espressolisp.object.literal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.tree.LispTreeNode;
import p0nki.espressolisp.utils.ToDebugJSON;

import java.util.List;

public class LispFunctionLiteral extends LispLiteral implements ToDebugJSON {

    private final List<String> argNames;
    private final LispTreeNode treeRoot;

    public LispFunctionLiteral(List<String> argNames, LispTreeNode treeRoot) {
        this.argNames = argNames;
        this.treeRoot = treeRoot;
    }

    public List<String> getArgNames() {
        return argNames;
    }

    public LispTreeNode getTreeRoot() {
        return treeRoot;
    }

    @Override
    public String lispStr() {
        return "function[" + argNames.size() + "]";
    }

    @Override
    public String toString() {
        return "function[" + String.join(" ", argNames) + "]";
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        JSONArray args = new JSONArray();
        argNames.forEach(args::put);
        return new JSONObject()
                .put("type", "function")
                .put("args", args)
                .put("body", treeRoot.toDebugJSON());
    }

    @Override
    public String getType() {
        return "function";
    }

}
