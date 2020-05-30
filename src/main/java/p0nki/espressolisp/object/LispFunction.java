package p0nki.espressolisp.object;

import p0nki.espressolisp.tree.LispTreeNode;

import java.util.List;

public class LispFunction extends LispLiteral {

    private final String name;
    private final List<String> argNames;
    private final LispTreeNode treeRoot;

    public LispFunction(String name, List<String> argNames, LispTreeNode treeRoot) {
        this.name = name;
        this.argNames = argNames;
        this.treeRoot = treeRoot;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgNames() {
        return argNames;
    }

    public LispTreeNode getTreeRoot() {
        return treeRoot;
    }

    @Override
    public String toString() {
        return "function[" + name + "]";
    }

    @Override
    public String getType() {
        return "function";
    }

}
