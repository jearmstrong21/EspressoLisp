package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.literal.*;
import p0nki.espressolisp.token.LispLiteralToken;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenType;
import p0nki.espressolisp.tree.controlflow.*;

import java.util.ArrayList;
import java.util.List;

public class LispASTCreator {

    private static final int MAX_NODE_LIST = 1000;

    private LispASTCreator() {

    }

    @SuppressWarnings("unchecked") // what the fuck java
    private static <T extends LispToken> T expect(List<LispToken> tokens, LispTokenType type) throws LispException {
        LispToken token = tokens.remove(0);
        if (token.getType() != type) throw LispException.expected(type, token);
        return (T) token;
    }

    private static List<LispTreeNode> readNodeList(List<LispToken> tokens, LispToken token) throws LispException {
        List<LispTreeNode> nodes = new ArrayList<>();
        for (int i = 0; i < MAX_NODE_LIST; i++) {
            if (tokens.size() == 0) throw LispException.prematureEnd(token);
            if (tokens.get(0).getType() == LispTokenType.RIGHT_PAREN) break;
            nodes.add(parse(tokens));
        }
        return nodes;
    }

    public static LispTreeNode parse(List<LispToken> tokens) throws LispException {
        LispToken first = tokens.remove(0);
        if (first.getType() == LispTokenType.LITERAL) {
            LispLiteralToken literal = (LispLiteralToken) first;
            if (literal.getNumber().isPresent())
                return new LispLiteralNode(new LispNumberLiteral(literal.getNumber().get()), literal);
            if (literal.getBoolean().isPresent())
                return new LispLiteralNode(new LispBooleanLiteral(literal.getBoolean().get()), literal);
            if (literal.getNull()) return new LispLiteralNode(LispNullLiteral.INSTANCE, literal);
            return new LispReferenceNode(literal.getValue(), literal);
        } else if (first.getType() == LispTokenType.QUOTE) {
            LispLiteralToken literal = expect(tokens, LispTokenType.LITERAL);
            expect(tokens, LispTokenType.QUOTE);
            return new LispLiteralNode(new LispStringLiteral(literal.getValue()), literal);
        } else if (first.getType() == LispTokenType.LEFT_PAREN) {
            LispToken tentativeSecond = tokens.get(0);
            if (tentativeSecond.getType() == LispTokenType.LITERAL) {
                LispLiteralToken tentative = (LispLiteralToken) tentativeSecond;
                switch (tentative.getValue()) {
                    case "func": {
                        expect(tokens, LispTokenType.LITERAL);
                        expect(tokens, LispTokenType.LEFT_BRACKET);
                        List<String> argNames = new ArrayList<>();
                        for (int i = 0; i < 100; i++) {
                            if (tokens.get(0).getType() == LispTokenType.RIGHT_BRACKET) break;
                            LispLiteralToken token = expect(tokens, LispTokenType.LITERAL);
                            if (argNames.contains(token.getValue()))
                                throw new LispException("Argument already exists", token);
                            argNames.add(token.getValue());
                        }
                        expect(tokens, LispTokenType.RIGHT_BRACKET);
                        LispTreeNode body = parse(tokens);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispLiteralNode(new LispFunctionLiteral(argNames, body), tentative);
                    }
                    case "do": {
                        expect(tokens, LispTokenType.LITERAL);
                        List<LispTreeNode> nodes = readNodeList(tokens, tentative);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispDoNode(nodes, tentative);
                    }
                    case "if": {
                        expect(tokens, LispTokenType.LITERAL);
                        LispTreeNode condition = parse(tokens);
                        LispTreeNode then = parse(tokens);
                        if (tokens.get(0).getType() == LispTokenType.RIGHT_PAREN) {
                            expect(tokens, LispTokenType.RIGHT_PAREN);
                            return new LispIfNode(condition, then, new LispLiteralNode(LispNullLiteral.INSTANCE, tokens.get(0)), tentative);
                        }
                        LispTreeNode otherwise = parse(tokens);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispIfNode(condition, then, otherwise, tentative);
                    }
                    case "for": {
                        expect(tokens, LispTokenType.LITERAL);
                        LispTreeNode initialization = parse(tokens);
                        LispTreeNode condition = parse(tokens);
                        LispTreeNode increment = parse(tokens);
                        LispTreeNode body = parse(tokens);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispForNode(initialization, condition, increment, body, tentative);
                    }
                    case "while": {
                        expect(tokens, LispTokenType.LITERAL);
                        LispTreeNode condition = parse(tokens);
                        LispTreeNode body = parse(tokens);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispWhileNode(condition, body, tentative);
                    }
                    case "list":{
                        expect(tokens, LispTokenType.LITERAL);
                        List<LispTreeNode> nodes = readNodeList(tokens, tentative);
                        expect(tokens, LispTokenType.RIGHT_PAREN);
                        return new LispListNode(nodes, tentative);
                    }
                }
            }
            LispTreeNode func = parse(tokens);
            List<LispTreeNode> args = readNodeList(tokens, tentativeSecond);
            expect(tokens, LispTokenType.RIGHT_PAREN);
            return new LispInvokeNode(func, args, tentativeSecond);
        } else {
            throw new LispException("Invalid token " + first, first);
        }
    }

}
