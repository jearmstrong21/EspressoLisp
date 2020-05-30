package p0nki.espressolisp.token;

import java.util.Optional;

public class LispUnquotedLiteralToken extends LispToken {

    private final String value;

    public LispUnquotedLiteralToken(String value, int startIndex, int endIndex) {
        super(LispTokenType.UNQUOTED_LITERAL, startIndex, endIndex);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Optional<Double> getNumber() {
        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    public Optional<Boolean> getBoolean() {
        if (value.equals("true")) return Optional.of(true);
        if (value.equals("false")) return Optional.of(false);
        return Optional.empty();
    }

    public boolean getNull(){
        return value.equals("null");
    }

    @Override
    public String toString() {
        return String.format("%-10s", value) + "UNQUOTED_LITERAL";
    }
}
