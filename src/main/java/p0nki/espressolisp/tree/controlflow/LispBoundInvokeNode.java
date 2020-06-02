package p0nki.espressolisp.tree.controlflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispFunctionLiteral;
import p0nki.espressolisp.object.literal.LispMapLiteral;
import p0nki.espressolisp.object.reference.LispReference;
import p0nki.espressolisp.object.reference.LispStandardReferenceImpl;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.ArrayList;
import java.util.List;

public class LispBoundInvokeNode extends LispTreeNode {

    private final LispTreeNode object;
    private final LispTreeNode func;
    private final List<LispTreeNode> args;

    public LispBoundInvokeNode(LispTreeNode object, LispTreeNode func, List<LispTreeNode> args, LispToken token) {
        super(token);
        this.object = object;
        this.func = func;
        this.args = args;
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        LispObject self = object.evaluate(context);
        LispMapLiteral map = self.fullyDereference().asMap();
        String key = func.evaluate(context).fullyDereference().asString().getValue();
        LispFunctionLiteral funcObject = map.getObjects().get(key).fullyDereference().asFunction();
        if (funcObject.getArgNames().size() != args.size() + 1)
            throw new LispException("Expected " + funcObject.getArgNames().size() + " arguments, found " + (args.size() + 1) + " arguments", getToken());
        List<LispObject> arguments = new ArrayList<>();
        for (LispTreeNode node : args) {
            arguments.add(node.evaluate(context));
        }
        arguments.add(0, self);
        LispContext push = context.push();
        for (int i = 0; i < funcObject.getArgNames().size(); i++) {
            push.overwrite(funcObject.getArgNames().get(i), new LispReference(funcObject.getArgNames().get(i), false, new LispStandardReferenceImpl(arguments.get(i))));
        }
        LispObject result = funcObject.getTreeRoot().evaluate(push);
        if (self instanceof LispReference) ((LispReference) self).set(push.evaluate("self"));
        return result;
    }

    @Override
    public JSONObject toDebugJSON() throws JSONException {
        JSONArray arr = new JSONArray();
        for (LispTreeNode node : args) {
            arr.put(node.toDebugJSON());
        }
        return new JSONObject()
                .put("type", "bound_invoke")
                .put("object", object.toDebugJSON())
                .put("func", func.toDebugJSON())
                .put("args", arr);
    }
}
