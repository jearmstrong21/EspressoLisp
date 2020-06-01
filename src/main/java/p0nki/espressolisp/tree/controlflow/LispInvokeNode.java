package p0nki.espressolisp.tree.controlflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.literal.LispFunctionLiteral;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.LispReference;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.List;

public class LispInvokeNode extends LispTreeNode {

    private final String name;
    private final List<LispTreeNode> args;

    public LispInvokeNode(String name, List<LispTreeNode> args, LispToken token) {
        super(token);
        this.name = name;
        this.args = args;
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        JSONArray arr = new JSONArray();
        args.forEach(node -> {
            try {
                arr.put(node.toDebugJSON());
            } catch (JSONException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
        return new JSONObject()
                .put("type", "invoke")
                .put("name", name)
                .put("args", arr);
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        LispObject object = context.get(name);
        while (object.isLValue()) object = object.get();
        if (!(object instanceof LispFunctionLiteral)) throw LispException.uncallableVariable(name, null);
        LispFunctionLiteral function = (LispFunctionLiteral) object;
//        if(function)
        if (function.getArgNames().size() < args.size())
            throw LispException.invalidArgList(function.getArgNames().size(), args.size(), null);
        LispContext pushed = context.push();
        for (int i = 0; i < args.size(); i++) {
            LispObject obj = args.get(i).evaluate(context);
//            System.out.println("ARG " + i + ": " + obj);
            pushed.overwrite(function.getArgNames().get(i), new LispReference(function.getArgNames().get(i), false, obj));
        }
        return function.getTreeRoot().evaluate(pushed);
    }

}
