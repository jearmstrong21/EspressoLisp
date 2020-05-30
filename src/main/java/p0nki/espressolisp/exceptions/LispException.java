package p0nki.espressolisp.exceptions;

import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenType;

public class LispException extends Exception {

    private LispToken token;

    private LispException(String message, LispToken token) {
        super(message);
        this.token = token;
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

    public static LispException tooManyArguments(LispToken token) {
        return new LispException("Too many arguments", token);
    }

    public static LispException uncallableVariable(String name, LispToken token) {
        return new LispException("Cannot call non-function " + name, token);
    }

    public static LispException invalidArgList(int expectedCount, int actualCount, LispToken token) {
        return new LispException("Expected " + expectedCount + " arguments, found " + actualCount + " arguments", token);
    }

    public static LispException unknownVariable(String name, LispToken token) {
        return new LispException("Unknown variable " + name, token);
    }

    public static LispException divisionByZero(LispToken token) {
        return new LispException("Division by zero", token);
    }

    public static LispException expected(LispTokenType expected, LispToken token) {
        return new LispException("Expected " + expected, token);
    }

    public static LispException expected2(LispTokenType expected1, LispTokenType expected2, LispToken token) {
        return new LispException("Expected " + expected1 + " or " + expected2, token);
    }

    public static LispException expected(LispTokenType expected, LispTokenType after, LispToken token) {
        return new LispException("Expected " + expected + " after " + after, token);
    }

    public LispToken getToken() {
        return token;
    }

    public LispException setToken(LispToken token) {
        this.token = token;
        return this;
    }

}
