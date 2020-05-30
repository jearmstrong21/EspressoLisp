package p0nki.racket.tree;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketBooleanLiteral;
import p0nki.racket.object.RacketNullObject;
import p0nki.racket.object.RacketNumberLiteral;
import p0nki.racket.object.RacketVariableReference;
import p0nki.racket.token.RacketToken;
import p0nki.racket.token.RacketTokenType;
import p0nki.racket.token.RacketUnquotedLiteralToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RacketASTCreator {

    private RacketASTCreator() {

    }

    //NOTE - only consumes tokens until it parses something. Tokens almost ALWAYS will have more.
    public static RacketTreeNode parseUnquotedLiteral(RacketUnquotedLiteralToken token) {
        Optional<Double> number = token.getNumber();
        if (number.isPresent()) return new RacketLiteralNode(new RacketNumberLiteral(number.get()));
        Optional<Boolean> bool = token.getBoolean();
        if (bool.isPresent()) return new RacketLiteralNode(new RacketBooleanLiteral(bool.get()));
        if (token.getNull()) return new RacketLiteralNode(RacketNullObject.INSTANCE);
        return new RacketLiteralNode(new RacketVariableReference(token.getValue()));
    }

    public static RacketTreeNode parse(List<RacketToken> tokens) throws RacketException {
        RacketToken first = tokens.get(0);
        if (first.getType() == RacketTokenType.LEFT_PAREN) {
            tokens.remove(0);
            RacketToken second = tokens.remove(0);
            if (second.getType() != RacketTokenType.UNQUOTED_LITERAL)
                throw RacketException.expected(RacketTokenType.UNQUOTED_LITERAL, RacketTokenType.LEFT_PAREN, second);
            RacketUnquotedLiteralToken op = (RacketUnquotedLiteralToken) second;
            List<RacketTreeNode> children = new ArrayList<>();
            boolean ended = false;
            for (int i = 0; i < 1000; i++) {
                if (tokens.get(0).getType() == RacketTokenType.RIGHT_PAREN) {
                    ended = true;
                    break;
                }
                if (tokens.get(0).getType() == RacketTokenType.UNQUOTED_LITERAL) {
                    children.add(parseUnquotedLiteral((RacketUnquotedLiteralToken) tokens.remove(0)));
                } else if (tokens.get(0).getType() == RacketTokenType.LEFT_PAREN) {
                    children.add(parse(tokens));
                } else {
                    throw RacketException.expected2(RacketTokenType.LEFT_PAREN, RacketTokenType.UNQUOTED_LITERAL, tokens.get(0));
                }
            }
            if (!ended) throw RacketException.tooManyArguments(op);
            RacketToken rightParen = tokens.remove(0);
            if (rightParen.getType() != RacketTokenType.RIGHT_PAREN)
                throw RacketException.expected(RacketTokenType.RIGHT_PAREN, rightParen);
            return new RacketIncompleteFunctionNode(op.getValue(), children);
        } else if (first.getType() == RacketTokenType.UNQUOTED_LITERAL) {
            return parseUnquotedLiteral((RacketUnquotedLiteralToken) tokens.remove(0));
        } else {
            throw RacketException.expected2(RacketTokenType.LEFT_PAREN, RacketTokenType.UNQUOTED_LITERAL, first);
        }
    }

}
