package p0nki.espressolisp.object;

import p0nki.espressolisp.tree.LispTreeNode;

import java.util.List;
import java.util.stream.Collectors;

public class LispFunction extends LispLiteral {

    private final List<String> argNames;
    private final LispTreeNode treeRoot;

    public LispFunction(List<String> argNames, LispTreeNode treeRoot) {
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
    public String toString() {
        return "function[" + String.join(",", argNames) + "]";
    }

    @Override
    public String getType() {
        return "function";
    }

}
