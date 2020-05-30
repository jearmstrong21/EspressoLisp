package p0nki.racket.run;

import org.junit.Test;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.function.RacketBinaryFunctionAdapter;
import p0nki.racket.function.RacketUnaryFunctionAdapter;
import p0nki.racket.object.*;
import p0nki.racket.token.RacketToken;
import p0nki.racket.token.RacketTokenizer;
import p0nki.racket.tree.RacketASTCreator;
import p0nki.racket.tree.RacketTreeNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RacketContextTest {

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
    public void test() throws RacketException {
        RacketContext context = new RacketContext();
        context.getObjects().put("+", new RacketCompleteFunction("+", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketNumberLiteral(arg1.asNumber().getValue() + arg2.asNumber().getValue());
        }));
        context.getObjects().put("-", new RacketCompleteFunction("-", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketNumberLiteral(arg1.asNumber().getValue() - arg2.asNumber().getValue());
        }));
        context.getObjects().put("*", new RacketCompleteFunction("*", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketNumberLiteral(arg1.asNumber().getValue() * arg2.asNumber().getValue());
        }));
        context.getObjects().put("/", new RacketCompleteFunction("/", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketNumberLiteral(arg1.asNumber().getValue() / arg2.asNumber().getValue());
        }));
        context.getObjects().put("inc", new RacketCompleteFunction("inc", of("arg1"), (RacketUnaryFunctionAdapter) (ctx, arg1) -> {
            if (arg1 == RacketNullObject.INSTANCE) return RacketNullObject.INSTANCE;
            return new RacketNumberLiteral(arg1.asNumber().getValue() + 1);
        }));
        context.getObjects().put("dec", new RacketCompleteFunction("dec", of("arg1"), (RacketUnaryFunctionAdapter) (ctx, arg1) -> {
            if (arg1 == RacketNullObject.INSTANCE) return RacketNullObject.INSTANCE;
            return new RacketNumberLiteral(arg1.asNumber().getValue() + 1);
        }));
        context.getObjects().put("and", new RacketCompleteFunction("and", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asBoolean().getValue() && arg2.asBoolean().getValue());
        }));
        context.getObjects().put("or", new RacketCompleteFunction("or", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asBoolean().getValue() || arg2.asBoolean().getValue());
        }));
        context.getObjects().put("xor", new RacketCompleteFunction("xor", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asBoolean().getValue() ^ arg2.asBoolean().getValue());
        }));
        context.getObjects().put("<", new RacketCompleteFunction("<", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asNumber().getValue() < arg2.asNumber().getValue());
        }));
        context.getObjects().put("<=", new RacketCompleteFunction("<=", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asNumber().getValue() <= arg2.asNumber().getValue());
        }));
        context.getObjects().put(">", new RacketCompleteFunction(">", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asNumber().getValue() > arg2.asNumber().getValue());
        }));
        context.getObjects().put(">=", new RacketCompleteFunction(">=", of("arg1", "arg2"), (RacketBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            if (arg1 == RacketNullObject.INSTANCE || arg2 == RacketNullObject.INSTANCE)
                return RacketNullObject.INSTANCE;
            return new RacketBooleanLiteral(arg1.asNumber().getValue() >= arg2.asNumber().getValue());
        }));

        // TODO: implement LVALUE and RVALUE distinction, pass in RacketTreeNode instead of RacketObject into RacketCompleteFunction?
        //  Good idea for lazy argument evaluation, especially in boolean operations
        //  Ideally and/or/xor all take in any number of arguments, rn its limited to two
//        String code = "(< (/ 4 2) (* 5 (inc 3)))";
//        List<RacketToken> tokens = RacketTokenizer.tokenize(code);
//        RacketTreeNode ast = RacketASTCreator.parse(tokens);
//        RacketObject result = ast.evaluate(context);
//        System.out.println(ast.debugStringify(""));
//        System.out.println(context.getObjects().keySet());
//        System.out.println(result);
        System.out.println(RacketASTCreator.parse(RacketTokenizer.tokenize("(+ (inc 2) 5)")).evaluate(context));
    }

}
