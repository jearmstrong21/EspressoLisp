package p0nki.racket.tree;

import p0nki.racket.object.RacketObject;

public class RacketLiteralNode implements RacketTreeNode {

    private final RacketObject value;

    public RacketLiteralNode(RacketObject value) {
        this.value = value;
    }

    public RacketObject getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "literal[" + value + "]";
    }

    @Override
    public RacketObject evaluate(RacketContext context) {
        return value;
    }

    @Override
    public String debugStringify(String indent) {
        return indent + toString();
    }
}
