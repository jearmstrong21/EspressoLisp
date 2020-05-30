package p0nki.espressolisp.run;

import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispBinaryFunctionAdapter;
import p0nki.espressolisp.function.LispUnaryFunctionAdapter;
import p0nki.espressolisp.object.*;
import p0nki.espressolisp.token.LispTokenizer;
import p0nki.espressolisp.tree.LispASTCreator;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LispContextTest {

    private static List<String> of(String a) {
        List<String> l = new ArrayList<>();
        l.add(a);
        return l;
    }

    private static List<String> of(String a, String b) {
        List<String> l = new ArrayList<>();
        l.add(a);
        l.add(b);
        return l;
    }

    @Test
    public void test() throws LispException {
        LispContext context = new LispContext();
        context.getObjects().put("+", new LispCompleteFunction("+", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + arg2.asNumber().getValue());
        }));
        context.getObjects().put("-", new LispCompleteFunction("-", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() - arg2.asNumber().getValue());
        }));
        context.getObjects().put("*", new LispCompleteFunction("*", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() * arg2.asNumber().getValue());
        }));
        context.getObjects().put("/", new LispCompleteFunction("/", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() / arg2.asNumber().getValue());
        }));
        context.getObjects().put("inc", new LispCompleteFunction("inc", of("arg1"), (LispUnaryFunctionAdapter) (ctx, arg1) -> {
            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
        }));
        context.getObjects().put("dec", new LispCompleteFunction("dec", of("arg1"), (LispUnaryFunctionAdapter) (ctx, arg1) -> {
            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
        }));
        context.getObjects().put("and", new LispCompleteFunction("and", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asBoolean().getValue() && arg2.asBoolean().getValue());
        }));
        context.getObjects().put("or", new LispCompleteFunction("or", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asBoolean().getValue() || arg2.asBoolean().getValue());
        }));
        context.getObjects().put("xor", new LispCompleteFunction("xor", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asBoolean().getValue() ^ arg2.asBoolean().getValue());
        }));
        context.getObjects().put("<", new LispCompleteFunction("<", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asNumber().getValue() < arg2.asNumber().getValue());
        }));
        context.getObjects().put("<=", new LispCompleteFunction("<=", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asNumber().getValue() <= arg2.asNumber().getValue());
        }));
        context.getObjects().put(">", new LispCompleteFunction(">", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asNumber().getValue() > arg2.asNumber().getValue());
        }));
        context.getObjects().put(">=", new LispCompleteFunction(">=", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispBooleanLiteral(arg1.asNumber().getValue() >= arg2.asNumber().getValue());
        }));

        // TODO: implement LVALUE and RVALUE distinction, pass in RacketTreeNode instead of RacketObject into RacketCompleteFunction?
        //  Good idea for lazy argument evaluation, especially in boolean operations
        //  Ideally and/or/xor all take in any number of arguments, rn its limited to two

        // TODO: fix bad naming in function p0nki.racket.package, esp with RacketCompleteFunction, FunctionalInterface, *FunctionAdapter, and related
        //  rename RacketVariableReference to RacketLRValue and RacketBooleanLiteral, RacketNumberLiteral to extend RacketRValue
//        String code = "(< (/ 4 2) (* 5 (inc 3)))";
//        List<RacketToken> tokens = RacketTokenizer.tokenize(code);
//        RacketTreeNode ast = RacketASTCreator.parse(tokens);
//        RacketObject result = ast.evaluate(context);
//        System.out.println(ast.debugStringify(""));
//        System.out.println(context.getObjects().keySet());
//        System.out.println(result);
        System.out.println(LispASTCreator.parse(LispTokenizer.tokenize("(and true true)")).evaluate(context));
    }

}
