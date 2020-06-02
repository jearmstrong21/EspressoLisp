package p0nki.espressolisp.tree.controlflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispNullLiteral;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.List;

public class LispDoNode extends LispTreeNode {

    private final List<LispTreeNode> nodes;

    public LispDoNode(List<LispTreeNode> nodes, LispToken token) {
        super(token);
        this.nodes = nodes;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        if (nodes.size() == 0) return LispNullLiteral.INSTANCE;
        for (int i = 0; i < nodes.size() - 1; i++) nodes.get(i).evaluate(context);
        return nodes.get(nodes.size() - 1).evaluate(context);
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        JSONArray arr = new JSONArray();
        nodes.forEach(node -> {
            try {
                arr.put(node.toDebugJSON());
            } catch (JSONException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
        return new JSONObject()
                .put("type", "do")
                .put("nodes", arr);
    }
}
