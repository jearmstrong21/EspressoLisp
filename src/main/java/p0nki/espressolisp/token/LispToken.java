package p0nki.espressolisp.token;

public class LispToken {

    private final LispTokenType type;
    private final int startIndex;
    private final int endIndex;

    public LispToken(LispTokenType type, int startIndex, int endIndex) {
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public LispTokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString() + "[" + startIndex + "," + endIndex + "]";
    }
}
