package p0nki.espressolisp.tree.controlflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispFunctionLiteral;
import p0nki.espressolisp.object.literal.LispNullLiteral;
import p0nki.espressolisp.object.reference.LispReference;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.List;

public class LispInvokeNode extends LispTreeNode {

    private final LispTreeNode func;
    private final List<LispTreeNode> args;

    public LispInvokeNode(LispTreeNode func, List<LispTreeNode> args, LispToken token) {
        super(token);
        this.func = func;
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
                .put("type", "function_invoke")
                .put("func", func.toDebugJSON())
                .put("args", arr);
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        LispFunctionLiteral funcObject = func.evaluate(context).fullyDereference().asFunction();
        if (funcObject.getArgNames().size() != args.size())
            throw new LispException("Expected " + funcObject.getArgNames().size() + " arguments, found " + args.size() + " arguments", getToken());
        LispContext push = context.push();
        for (int i = 0; i < funcObject.getArgNames().size(); i++) {
            final LispObject initialValue = args.get(i).evaluate(context);
            push.overwrite(funcObject.getArgNames().get(i), new LispReference(funcObject.getArgNames().get(i), false, new LispReference.Impl() {
                private LispObject value = initialValue;

                @Override
                public void set(LispObject newValue) {
                    value = newValue;
                }

                @Override
                public LispObject get() {
                    return value;
                }

                @Override
                public void delete() {
                    value = LispNullLiteral.INSTANCE;
                }
            }));
//            push.overwrite(funcObject.getArgNames().get(i), new LispObjectReference(funcObject.getArgNames().get(i), false, args.get(i).evaluate(context)));
        }
        return funcObject.getTreeRoot().evaluate(push);
    }

}
