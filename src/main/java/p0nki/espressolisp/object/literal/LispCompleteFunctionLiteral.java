package p0nki.espressolisp.object.literal;

import org.json.JSONException;
import org.json.JSONObject;
import p0nki.espressolisp.adapter.LispFunctionalInterface;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.ArrayList;
import java.util.List;

public class LispCompleteFunctionLiteral extends LispFunctionLiteral {

    public LispCompleteFunctionLiteral(List<String> argNames, LispFunctionalInterface function) {
        super(argNames, new LispTreeNode(null) {
            @Override
            public LispObject evaluate(LispContext context) throws LispException {
                List<LispObject> args = new ArrayList<>();
                for (String argName : argNames) {
                    args.add(context.get(argName));
                }
                return function.evaluate(context, args);
            }

            @Override
            public JSONObject toDebugJSON() throws JSONException {
                return new JSONObject()
                        .put("type", "complete_function")
                        .put("argNames", argNames);
            }

        });
    }

}
