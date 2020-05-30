package p0nki.racket.tree;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketFunction;
import p0nki.racket.object.RacketObject;

import java.util.List;
import java.util.stream.Collectors;

public class RacketExpressionNode implements RacketTreeNode {

    private final String name;
    private final List<RacketTreeNode> args;

    public RacketExpressionNode(String name, List<RacketTreeNode> args) {
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
    public RacketObject evaluate(RacketContext context) throws RacketException {
        RacketObject object = context.get(name);
        if (!(object instanceof RacketFunction)) throw RacketException.uncallableVariable(name, null);
        RacketFunction function = (RacketFunction) object;
        if (function.getArgNames().size() != args.size())
            throw RacketException.invalidArgList(function.getArgNames().size(), args.size(), null);
        RacketContext pushed = context.push();
        for (int i = 0; i < function.getArgNames().size(); i++) {
            pushed.getObjects().put(function.getArgNames().get(i), args.get(i).evaluate(context));
        }
        return function.getTreeRoot().evaluate(context);
    }

}
