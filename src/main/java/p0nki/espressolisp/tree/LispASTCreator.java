package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.literal.*;
import p0nki.espressolisp.token.LispLiteralToken;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenType;
import p0nki.espressolisp.tree.controlflow.*;

import java.util.ArrayList;
import java.util.List;

// TODO add a <T> expectToken(List<Token>, TokenType) which casts tokens.remove(0) as T and throws "expected" if type doesn't match

public class LispASTCreator {

    private LispASTCreator() {

    }

    @SuppressWarnings("unchecked") // what the fuck java
    private static <T extends LispToken> T expect(List<LispToken> tokens, LispTokenType type) throws LispException {
        LispToken token = tokens.remove(0);
        if (token.getType() != type) throw LispException.expected(type, token);
        return (T) token;
    }

    // TODO make new nodes instead of instantiating classes inline
    // TODO list literal, hardest part will be grammar for add / rem

    public static LispTreeNode parse(List<LispToken> tokens) throws LispException {
        LispToken first = tokens.get(0);
        if (first.getType() == LispTokenType.LEFT_PAREN) {
            tokens.remove(0);
            LispToken second = tokens.remove(0);
            if (second.getType() == LispTokenType.RIGHT_PAREN)
                return new LispLiteralNode(LispNullLiteral.INSTANCE, second);
            if (second.getType() != LispTokenType.LITERAL)
                throw LispException.expected(LispTokenType.LITERAL, LispTokenType.LEFT_PAREN, second);
            LispLiteralToken op = (LispLiteralToken) second;
            switch (op.getValue()) {
                case "func":
                    LispToken leftBracket = expect(tokens, LispTokenType.LEFT_BRACKET);
                    List<String> argNames = new ArrayList<>();
                    LispToken prevToken = leftBracket;
                    for (int i = 0; i < 1000; i++) {
                        LispToken token = tokens.remove(0);
                        if (token.getType() == LispTokenType.RIGHT_BRACKET) {
                            prevToken = token;
                            break;
                        }
                        if (token.getType() != LispTokenType.LITERAL) {
                            throw LispException.expected(LispTokenType.LITERAL, prevToken.getType(), token);
                        }
                        prevToken = token;
                        argNames.add(((LispLiteralToken) token).getValue());
                    }
                    if (prevToken.getType() != LispTokenType.RIGHT_BRACKET) {
                        throw LispException.tooManyArguments(prevToken);
                    }
                    LispTreeNode treeNode = parse(tokens);
                    expect(tokens, LispTokenType.RIGHT_PAREN);
                    return new LispLiteralNode(new LispFunctionLiteral(argNames, treeNode), op);
                case "for": {
                    LispTreeNode initialize = parse(tokens);
                    LispTreeNode condition = parse(tokens);
                    LispTreeNode increment = parse(tokens);
                    LispTreeNode body = parse(tokens);
                    expect(tokens, LispTokenType.RIGHT_PAREN);
                    return new LispForNode(initialize, condition, increment, body, op);
                }
                case "while": {
                    LispTreeNode condition = parse(tokens);
                    LispTreeNode body = parse(tokens);
                    expect(tokens, LispTokenType.RIGHT_PAREN);
                    return new LispWhileNode(condition, body, op);
                }
                case "if": {
                    LispTreeNode condition = parse(tokens);
                    LispTreeNode then = parse(tokens);
                    LispToken nextToken = tokens.get(0);
                    if (nextToken.getType() != LispTokenType.RIGHT_PAREN) {
                        LispTreeNode otherwise = parse(tokens);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispIfNode(condition, then, otherwise, op);
                    } else {
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispIfNode(condition, then, new LispLiteralNode(LispNullLiteral.INSTANCE, op), op);
                    }
                }
            }
            List<LispTreeNode> children = new ArrayList<>();
            boolean ended = false;
            for (int i = 0; i < 1000; i++) {
                if (tokens.size() == 0) throw LispException.prematureEnd(second);
                if (tokens.get(0).getType() == LispTokenType.RIGHT_PAREN) {
                    ended = true;
                    break;
                }
                children.add(parse(tokens));
            }
            if (!ended) throw LispException.tooManyArguments(op);
            expect(tokens, LispTokenType.RIGHT_PAREN);
            if (op.getValue().equals("do")) {
                return new LispDoNode(children, op);
            }
            return new LispInvokeNode(op.getValue(), children, op);
        } else if (first.getType() == LispTokenType.LITERAL) {
            LispLiteralToken literal = expect(tokens, LispTokenType.LITERAL);
            if (literal.getNumber().isPresent())
                return new LispLiteralNode(new LispNumberLiteral(literal.getNumber().get()), literal);
            if (literal.getBoolean().isPresent())
                return new LispLiteralNode(new LispBooleanLiteral(literal.getBoolean().get()), literal);
            if (literal.getNull()) return new LispLiteralNode(LispNullLiteral.INSTANCE, literal);
            return new LispReferenceNode(literal.getValue(), literal);
        } else if (first.getType() == LispTokenType.QUOTE) {
            expect(tokens, LispTokenType.QUOTE);
            LispLiteralToken literal = expect(tokens, LispTokenType.LITERAL);
            expect(tokens, LispTokenType.QUOTE);
            return new LispLiteralNode(new LispStringLiteral(literal.getValue()), first);
        } else {
            throw LispException.expected2(LispTokenType.LEFT_PAREN, LispTokenType.LITERAL, first);
        }
    }

}
