package p0nki.racket.exceptions;

import p0nki.racket.token.RacketToken;
import p0nki.racket.token.RacketTokenType;

public class RacketException extends Exception {

    private RacketToken token;

    private RacketException(String message, RacketToken token) {
        super(message);
        this.token = token;
    }

    public static RacketException tooManyArguments(RacketToken token) {
        return new RacketException("Too many arguments", token);
    }

    public static RacketException uncallableVariable(String name, RacketToken token) {
        return new RacketException("Cannot call non-function " + name, token);
    }

    public static RacketException invalidArgList(int expectedCount, int actualCount, RacketToken token) {
        return new RacketException("Expected " + expectedCount + " arguments, found " + actualCount + " arguments", token);
    }

    public static RacketException unknownVariable(String name, RacketToken token) {
        return new RacketException("Unknown variable " + name, token);
    }

    public static RacketException divisionByZero(RacketToken token) {
        return new RacketException("Division by zero", token);
    }

    public static RacketException expected(RacketTokenType expected, RacketToken token) {
        return new RacketException("Expected " + expected, token);
    }

    public static RacketException expected2(RacketTokenType expected1, RacketTokenType expected2, RacketToken token) {
        return new RacketException("Expected " + expected1 + " or " + expected2, token);
    }

    public static RacketException expected(RacketTokenType expected, RacketTokenType after, RacketToken token) {
        return new RacketException("Expected " + expected + " after " + after, token);
    }

    public RacketToken getToken() {
        return token;
    }

    public RacketException setToken(RacketToken token) {
        this.token = token;
        return this;
    }

}
