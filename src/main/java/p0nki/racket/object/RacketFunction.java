package p0nki.racket.object;

import p0nki.racket.tree.RacketTreeNode;

import java.util.List;

public class RacketFunction extends RacketObject {

    private final String name;
    private final List<String> argNames;
    private final RacketTreeNode treeRoot;

    public RacketFunction(String name, List<String> argNames, RacketTreeNode treeRoot) {
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

    public RacketTreeNode getTreeRoot() {
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
