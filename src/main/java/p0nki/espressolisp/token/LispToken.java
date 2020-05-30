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
//        switch (type) {
//            case LEFT_PAREN:
//                return "(         LEFT_PAREN";
//            case RIGHT_PAREN:
//                return ")         RIGHT_PAREN";
//            case LEFT_BRACKET:
//                return "[         LEFT_BRACKET";
//            case RIGHT_BRACKET:
//                return "]         RIGHT_BRACKET";
//            case COMMA:
//                return ",         COMMA";
//            default: // UNQUOTED_LITERAL
//                throw new UnsupportedOperationException();
//        }
        return type.toString() + "[" + startIndex + "," + endIndex + "]";
    }
}
