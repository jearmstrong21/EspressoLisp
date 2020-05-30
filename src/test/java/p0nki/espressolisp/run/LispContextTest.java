package p0nki.espressolisp.run;

import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispBinaryFunctionAdapter;
import p0nki.espressolisp.object.LispCompleteFunction;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispNumberLiteral;
import p0nki.espressolisp.object.LispVariableReference;
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
        LispContext context = new LispContext(null);
        context.getObjects().put("+", new LispVariableReference("+", new LispCompleteFunction("+", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + arg2.asNumber().getValue());
        })));
        context.getObjects().put("-", new LispVariableReference("-", new LispCompleteFunction("-", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() - arg2.asNumber().getValue());
        })));
        context.getObjects().put("*", new LispVariableReference("*", new LispCompleteFunction("*", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() * arg2.asNumber().getValue());
        })));
        context.getObjects().put("/", new LispVariableReference("/", new LispCompleteFunction("/", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() / arg2.asNumber().getValue());
        })));
        context.getObjects().put("=", new LispVariableReference("=", new LispCompleteFunction("=", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.get();
            arg2 = arg2.fullyDereference();
            System.out.println();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            LispVariableReference ref = (LispVariableReference) arg1;
            if (ctx.getObjects().containsKey(ref.getName())) {
                LispVariableReference trueRef = (LispVariableReference) ctx.get(ref.getName());
                trueRef.set(arg2);
            } else {
                ref.set(arg2);
                ctx.getParent().getObjects().put(ref.getName(), ref);
            }
            return arg2;
        })));
//        context.getObjects().put("inc", new LispCompleteFunction("inc", of("arg1"), (LispUnaryFunctionAdapter) (ctx, arg1) -> {
//            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
//            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
//        }));
//        context.getObjects().put("dec", new LispCompleteFunction("dec", of("arg1"), (LispUnaryFunctionAdapter) (ctx, arg1) -> {
//            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
//            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
//        }));
//        context.getObjects().put("and", new LispCompleteFunction("and", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asBoolean().getValue() && arg2.asBoolean().getValue());
//        }));
//        context.getObjects().put("or", new LispCompleteFunction("or", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asBoolean().getValue() || arg2.asBoolean().getValue());
//        }));
//        context.getObjects().put("xor", new LispCompleteFunction("xor", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asBoolean().getValue() ^ arg2.asBoolean().getValue());
//        }));
//        context.getObjects().put("<", new LispCompleteFunction("<", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asNumber().getValue() < arg2.asNumber().getValue());
//        }));
//        context.getObjects().put("<=", new LispCompleteFunction("<=", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asNumber().getValue() <= arg2.asNumber().getValue());
//        }));
//        context.getObjects().put(">", new LispCompleteFunction(">", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asNumber().getValue() > arg2.asNumber().getValue());
//        }));
//        context.getObjects().put(">=", new LispCompleteFunction(">=", of("arg1", "arg2"), (LispBinaryFunctionAdapter) (ctx, arg1, arg2) -> {
//            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
//                return LispNullObject.INSTANCE;
//            return new LispBooleanLiteral(arg1.asNumber().getValue() >= arg2.asNumber().getValue());
//        }));

        // TODO: implement LVALUE and RVALUE distinction, pass in LispTreeNode instead of LispObject into LispCompleteFunction?
        //  Good idea for lazy argument evaluation, especially in boolean operations
        //  Ideally and/or/xor all take in any number of arguments, rn its limited to two

        // TODO: fix bad naming in function p0nki.racket.package, esp with LispCompleteFunction, FunctionalInterface, *FunctionAdapter, and related
        //  rename LispVariableReference to LispLRValue and LispBooleanLiteral, LispNumberLiteral to extend LispRValue
//        String code = "(= x (* 5 3))";
//        List<LispToken> tokens = LispTokenizer.tokenize(code);
//        LispTreeNode ast = LispASTCreator.parse(tokens);
//        System.out.println(ast.debugStringify(""));
//        LispObject result = ast.evaluate(context);
//        System.out.println("OBJECTS\n" + context.getObjects().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().get()).collect(Collectors.joining("\n")));
//        System.out.println("RESULT\n" + result);
//        System.out.println();
//        System.out.println(LispASTCreator.parse(LispTokenizer.tokenize("(+ 5 x)")).evaluate(context));
//        LispVariableReference reference = new LispVariableReference("x", new LispNumberLiteral(5));
//        System.out.println(reference);
//        System.out.println(reference.get());
//        System.out.println(reference.get().isLValue());
//        System.out.println(reference.fullyDereference());
//        context.getObjects().put("x", new LispVariableReference("x", new LispNumberLiteral(5)));
        LispASTCreator.parse(LispTokenizer.tokenize("(= x (* 3 5))")).evaluate(context);
        System.out.println(LispASTCreator.parse(LispTokenizer.tokenize("(+ 5 x)")).evaluate(context));
//        System.out.println(LispASTCreator.parse(LispTokenizer.tokenize("x")).evaluate(context).fullyDereference());
    }

}
