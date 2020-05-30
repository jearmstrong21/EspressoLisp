package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispBooleanLiteral;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispNumberLiteral;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenType;
import p0nki.espressolisp.token.LispUnquotedLiteralToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LispASTCreator {

    private LispASTCreator() {

    }

    //NOTE - only consumes tokens until it parses something. Tokens almost ALWAYS will have more.
    public static LispTreeNode parseUnquotedLiteral(LispUnquotedLiteralToken token) {
        Optional<Double> number = token.getNumber();
        if (number.isPresent()) return new LispLiteralNode(new LispNumberLiteral(number.get()));
        Optional<Boolean> bool = token.getBoolean();
        if (bool.isPresent()) return new LispLiteralNode(new LispBooleanLiteral(bool.get()));
        if (token.getNull()) return new LispLiteralNode(LispNullObject.INSTANCE);
        return new LispVariableNode(token.getValue());
//        return new LispLiteralNode(new LispVariableReference(token.getValue(), LispNullObject.INSTANCE));
    }

    public static LispTreeNode parse(List<LispToken> tokens) throws LispException {
        LispToken first = tokens.get(0);
        if (first.getType() == LispTokenType.LEFT_PAREN) {
            tokens.remove(0);
            LispToken second = tokens.remove(0);
            if (second.getType() != LispTokenType.UNQUOTED_LITERAL)
                throw LispException.expected(LispTokenType.UNQUOTED_LITERAL, LispTokenType.LEFT_PAREN, second);
            LispUnquotedLiteralToken op = (LispUnquotedLiteralToken) second;
            List<LispTreeNode> children = new ArrayList<>();
            boolean ended = false;
            for (int i = 0; i < 1000; i++) { // TODO: this is where I will check for function definition: if op.value() === "define" or whatever it will skip loop entirely and go solely to function, which will be of the format `define [arg1, arg2, arg....] (obj)`
                //TODO: add support for raw objects, i.e. just parse `null`
                if (tokens.get(0).getType() == LispTokenType.RIGHT_PAREN) {
                    ended = true;
                    break;
                }
                if (tokens.get(0).getType() == LispTokenType.UNQUOTED_LITERAL) {
                    children.add(parseUnquotedLiteral((LispUnquotedLiteralToken) tokens.remove(0)));
                } else if (tokens.get(0).getType() == LispTokenType.LEFT_PAREN) {
                    children.add(parse(tokens));
                } else {
                    throw LispException.expected2(LispTokenType.LEFT_PAREN, LispTokenType.UNQUOTED_LITERAL, tokens.get(0));
                }
            }
            if (!ended) throw LispException.tooManyArguments(op);
            LispToken rightParen = tokens.remove(0);
            if (rightParen.getType() != LispTokenType.RIGHT_PAREN)
                throw LispException.expected(LispTokenType.RIGHT_PAREN, rightParen);
            return new LispIncompleteFunctionNode(op.getValue(), children);
        } else if (first.getType() == LispTokenType.UNQUOTED_LITERAL) {
            return parseUnquotedLiteral((LispUnquotedLiteralToken) tokens.remove(0));
        } else {
            throw LispException.expected2(LispTokenType.LEFT_PAREN, LispTokenType.UNQUOTED_LITERAL, first);
        }
    }

}
