package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.*;
import p0nki.espressolisp.run.LispContext;
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
    }

    public static LispTreeNode parse(List<LispToken> tokens) throws LispException {
        LispToken first = tokens.get(0);
        if (first.getType() == LispTokenType.LEFT_PAREN) {
            tokens.remove(0);
            LispToken second = tokens.remove(0);
            if (second.getType() == LispTokenType.RIGHT_PAREN) return new LispLiteralNode(LispNullObject.INSTANCE);
            if (second.getType() != LispTokenType.UNQUOTED_LITERAL)
                throw LispException.expected(LispTokenType.UNQUOTED_LITERAL, LispTokenType.LEFT_PAREN, second);
            LispUnquotedLiteralToken op = (LispUnquotedLiteralToken) second;
            if (op.getValue().equals("func")) { // FUNCTION DECLARATION
                LispToken leftBracket = tokens.remove(0);
                if (leftBracket.getType() != LispTokenType.LEFT_BRACKET)
                    throw LispException.expected(LispTokenType.LEFT_BRACKET, LispTokenType.UNQUOTED_LITERAL, leftBracket);
                List<String> argNames = new ArrayList<>();
                boolean lookingForComma = false;
                LispToken prevToken = leftBracket;
                for (int i = 0; i < 1000; i++) {
                    LispToken token = tokens.remove(0);
                    if (token.getType() == LispTokenType.RIGHT_BRACKET) {
                        prevToken = token;
                        break;
                    }
                    if (lookingForComma) {
                        if (token.getType() != LispTokenType.COMMA) {
                            throw LispException.expected(LispTokenType.COMMA, prevToken.getType(), token);
                        }
                        lookingForComma = false;
                        prevToken = token;
                    } else {
                        if (token.getType() != LispTokenType.UNQUOTED_LITERAL) {
                            throw LispException.expected(LispTokenType.UNQUOTED_LITERAL, prevToken.getType(), token);
                        }
                        lookingForComma = true;
                        prevToken = token;
                        argNames.add(((LispUnquotedLiteralToken) token).getValue());
                    }
                }
                if (prevToken.getType() != LispTokenType.RIGHT_BRACKET) {
                    throw LispException.tooManyArguments(prevToken);
                }
                LispTreeNode treeNode = parse(tokens);
                LispToken lastToken = tokens.remove(0);
                if (lastToken.getType() != LispTokenType.RIGHT_PAREN) {
                    throw LispException.expected(LispTokenType.RIGHT_PAREN, lastToken);
                }
                return new LispLiteralNode(new LispFunction(argNames, treeNode));
            } else if (op.getValue().equals("for")) {
                LispTreeNode initialize = parse(tokens);
                LispTreeNode condition = parse(tokens);
                LispTreeNode increment = parse(tokens);
                LispTreeNode body = parse(tokens);
                LispToken lastToken = tokens.remove(0);
                if (lastToken.getType() != LispTokenType.RIGHT_PAREN) {
                    throw LispException.expected(LispTokenType.RIGHT_PAREN, lastToken);
                }
                return new LispTreeNode() {
                    @Override
                    public LispObject evaluate(LispContext context) throws LispException {
                        initialize.evaluate(context);
                        while (condition.evaluate(context).fullyDereference().asBoolean().getValue()) {
                            body.evaluate(context.push());
                            increment.evaluate(context);
                        }
                        return LispNullObject.INSTANCE;
                    }

                    @Override
                    public String debugStringify(String indent) {
                        return null;
                    }
                };
            }
            List<LispTreeNode> children = new ArrayList<>();
            boolean ended = false;
            for (int i = 0; i < 1000; i++) {
                //TODO: add support for raw objects, i.e. just parse `null`
                if (tokens.size() == 0) throw LispException.prematureEnd(second);
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
            return new ListFunctionInvokeNode(op.getValue(), children);
        } else if (first.getType() == LispTokenType.UNQUOTED_LITERAL) {
            return parseUnquotedLiteral((LispUnquotedLiteralToken) tokens.remove(0));
        } else {
            throw LispException.expected2(LispTokenType.LEFT_PAREN, LispTokenType.UNQUOTED_LITERAL, first);
        }
    }

}
