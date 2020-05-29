package p0nki.racket.exceptions;

import p0nki.racket.token.RacketToken;
import p0nki.racket.token.CLJTokenType;

public class RacketException extends Exception {

    private final RacketToken token;

    private RacketException(String message, RacketToken token) {
        super(message);
        this.token = token;
    }

    public static RacketException expected(CLJTokenType expected, RacketToken token) {
        return new RacketException("Expected " + expected, token);
    }

    public static RacketException expected(CLJTokenType expected, CLJTokenType after, RacketToken token) {
        return new RacketException("Expected " + expected + " after " + after, token);
    }

    public RacketToken getToken() {
        return token;
    }

}
