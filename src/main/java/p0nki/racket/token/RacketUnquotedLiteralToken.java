package p0nki.racket.token;

import java.util.Optional;

public class RacketUnquotedLiteralToken extends RacketToken {

    private final String value;

    public RacketUnquotedLiteralToken(String value, int startIndex, int endIndex) {
        super(RacketTokenType.UNQUOTED_LITERAL, startIndex, endIndex);
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
