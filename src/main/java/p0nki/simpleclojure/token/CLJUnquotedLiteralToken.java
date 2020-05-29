package p0nki.simpleclojure.token;

import java.math.BigDecimal;
import java.util.Optional;

public class CLJUnquotedLiteralToken extends CLJToken {

    private final String value;

    public CLJUnquotedLiteralToken(String value) {
        super(CLJTokenType.UNQUOTED_LITERAL);
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
