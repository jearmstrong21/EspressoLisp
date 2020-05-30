package p0nki.racket.token;

import java.math.BigDecimal;
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

    public Optional<BigDecimal> getNumeric() {
        try {
            return Optional.of(new BigDecimal(value));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return String.format("%-10s", value) + "UNQUOTED_LITERAL";
    }
}
