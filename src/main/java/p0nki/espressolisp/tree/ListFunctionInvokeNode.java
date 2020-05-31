package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispFunction;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.LispVariableReference;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.token.LispToken;

import java.util.List;
import java.util.stream.Collectors;

public class ListFunctionInvokeNode extends LispTreeNode {

    private final String name;
    private final List<LispTreeNode> args;

    public ListFunctionInvokeNode(String name, List<LispTreeNode> args, LispToken token) {
        super(token);
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        return "expression[" + name + ",\n" + args.stream().map(node -> node.debugStringify("\t")).collect(Collectors.joining("\n")) + "]";
    }

    @Override
    public String debugStringify(String indent) {
        return indent + "expression[" + name + ",\n" + args.stream().map(node -> node.debugStringify("\t" + indent)).collect(Collectors.joining("\n")) + "\n" + indent + "]";
    }

    @Override
    public LispObject evaluate(LispContext context) throws LispException {
        LispObject object = context.get(name);
        while (object.isLValue()) object = object.get();
        if (!(object instanceof LispFunction)) throw LispException.uncallableVariable(name, null);
        LispFunction function = (LispFunction) object;
        if (function.getArgNames().size() != args.size())
            throw LispException.invalidArgList(function.getArgNames().size(), args.size(), null);
        LispContext pushed = context.push();
        for (int i = 0; i < function.getArgNames().size(); i++) {
            LispObject obj = args.get(i).evaluate(context);
            pushed.set(function.getArgNames().get(i), new LispVariableReference(function.getArgNames().get(i), false, obj));
        }
        return function.getTreeRoot().evaluate(pushed);
    }

}
