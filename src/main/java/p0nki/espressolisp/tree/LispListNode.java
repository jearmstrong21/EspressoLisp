package p0nki.espressolisp.tree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispListLiteral;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

import java.util.ArrayList;
import java.util.List;

public class LispListNode extends LispTreeNode {

    private final List<LispTreeNode> nodes;

    public LispListNode(List<LispTreeNode> nodes, LispToken token) {
        super(token);
        this.nodes = nodes;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        List<LispObject> objects = new ArrayList<>();
        for (LispTreeNode node : nodes) objects.add(node.evaluate(context));
        return new LispListLiteral(objects);
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
                .put("type", "uninvoked_list")
                .put("elements", "");
    }
}
