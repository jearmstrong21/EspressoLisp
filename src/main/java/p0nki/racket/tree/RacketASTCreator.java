package p0nki.racket.tree;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketNumericLiteral;
import p0nki.racket.token.RacketToken;
import p0nki.racket.token.RacketTokenType;
import p0nki.racket.token.RacketUnquotedLiteralToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RacketASTCreator {

    private RacketASTCreator() {

    }

    //NOTE - only consumes tokens until it parses something. Tokens almost ALWAYS will have more.
    public static RacketTreeNode parseNonExpression(List<RacketToken> tokens) throws RacketException {
        RacketToken token = tokens.remove(0);
        if (token.getType() != RacketTokenType.UNQUOTED_LITERAL)
            throw RacketException.expected(RacketTokenType.UNQUOTED_LITERAL, token);
        RacketUnquotedLiteralToken unquotedLiteral = (RacketUnquotedLiteralToken) token;
        Optional<BigDecimal> numeric = unquotedLiteral.getNumeric();
        if (numeric.isPresent()) return new RacketLiteralNode(new RacketNumericLiteral(numeric.get()));
        return new RacketVariableNode(unquotedLiteral.getValue());
    }

    public static RacketTreeNode parse(List<RacketToken> tokens) throws RacketException {
        RacketToken first = tokens.get(0);
        if (first.getType() == RacketTokenType.LEFT_PAREN) {
            tokens.remove(0);
            RacketToken op = tokens.remove(0);
            RacketToken startArg;
            List<RacketTreeNode> children = new ArrayList<>();
            boolean ended = false;
            for (int i = 0; i < 1000; i++) {
                startArg = tokens.get(0);
                if (startArg.getType() == RacketTokenType.RIGHT_PAREN) {
                    ended = true;
                    break;
                }
                if (startArg.getType() == RacketTokenType.UNQUOTED_LITERAL || startArg.getType() == RacketTokenType.LEFT_PAREN) {
                    children.add(parse(tokens));
                }
            }
            if (!ended) throw RacketException.tooManyArguments(op);
            return new RacketExpressionNode(((RacketUnquotedLiteralToken) op).getValue(), children);
        } else if (first.getType() == RacketTokenType.UNQUOTED_LITERAL) {
            return parseNonExpression(tokens);
        } else {
            throw RacketException.expected2(RacketTokenType.LEFT_PAREN, RacketTokenType.UNQUOTED_LITERAL, first);
        }
    }

}
