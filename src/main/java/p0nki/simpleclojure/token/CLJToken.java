package p0nki.simpleclojure.token;

public class CLJToken {

    private final CLJTokenType type;

    public CLJToken(CLJTokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        switch (type) {
            case LEFT_PAREN:
                return "(         LEFT_PAREN";
            case RIGHT_PAREN:
                return ")         RIGHT_PAREN";
            case LEFT_BRACKET:
                return "[         LEFT_BRACKET";
            case RIGHT_BRACKET:
                return "]         RIGHT_BRACKET";
            case COMMA:
                return ",         COMMA";
            default: // UNQUOTED_LITERAL
                throw new UnsupportedOperationException();
        }
    }
}
