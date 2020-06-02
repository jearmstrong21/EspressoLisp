package p0nki.espressolisp.exceptions;

import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenType;

public class LispException extends Exception {

    private final LispToken token;

    public LispException(String message, LispToken token) {
        super(message);
        this.token = token;
    }

    public static LispException noParentContext(LispToken token) {
        return new LispException("No parent context", token);
    }

    public static LispException notInteger(double value, LispToken token) {
        return new LispException("Expected integer, got " + value, token);
    }

    public static LispException noLibraryWithName(String name, LispToken token) {
        return new LispException("No library with name " + name, token);
    }

    public static LispException unexpectedNull(LispToken token) {
        return new LispException("Unexpected null", token);
    }

    public static LispException prematureEnd(LispToken token) {
        return new LispException("Premature end", token);
    }

    public static LispException invalidValueType(boolean expectedLValue, boolean gotLValue, LispToken token) {
        return new LispException("Invalid value. Expected " + (expectedLValue ? "lvalue" : "rvalue") + ", got " + (gotLValue ? "lvalue" : "rvalue"), token);
    }

    public static LispException invalidType(String expectedType, String actual, LispToken token) {
        return new LispException("Invalid type. Expected " + expectedType + ", got " + actual, token);
    }

    public static LispException invalidArgList(int expectedCount, int actualCount, LispToken token) {
        return new LispException("Expected " + expectedCount + " arguments, found " + actualCount + " arguments", token);
    }

    public static LispException expected(LispTokenType expected, LispToken token) {
        return new LispException("Expected " + expected, token);
    }

    public LispToken getToken() {
        return token;
    }

}
