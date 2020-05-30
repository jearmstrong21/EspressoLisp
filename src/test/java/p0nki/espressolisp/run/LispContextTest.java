package p0nki.espressolisp.run;

import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispDyadAdapter;
import p0nki.espressolisp.function.LispMonadAdapter;
import p0nki.espressolisp.object.*;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenizer;
import p0nki.espressolisp.tree.LispASTCreator;
import p0nki.espressolisp.tree.LispTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private void run(LispContext context, String code) {
        List<LispToken> tokens = LispTokenizer.tokenize(code);
        System.out.println(code);
        try {
            LispTreeNode tree = LispASTCreator.parse(new ArrayList<>(tokens));
            LispObject res = tree.evaluate(context);
            System.out.print("=> " + res);
            while (res.isLValue()) {
                res = res.get();
                System.out.print(" = " + res);
            }
            System.out.println();
        } catch (LispException exc) {
            if (exc.getToken() == null) {
                System.out.println(exc.getMessage());
                System.out.println("-- Runtime error --");
            } else {
                int start = exc.getToken().getStartIndex();
                int end = exc.getToken().getEndIndex();
                for (int i = 0; i < start; i++)
                    System.out.print(" ");
                for (int i = 0; i < end - start; i++) System.out.print("^");
                System.out.println(" " + exc.getMessage());
                System.out.println("--- Parse error: " + exc.getToken() + " ---");

                System.out.println();
                System.out.println("TOKENS:");
                System.out.println(tokens.stream().map(LispToken::toString).collect(Collectors.joining("\n")));
            }
            exc.printStackTrace();
            System.exit(1);
        }
        System.out.println();
    }

    @Test
    public void test() throws LispException {
        LispContext context = new LispContext(null);
        context.getObjects().put("+", new LispVariableReference("+", new LispCompleteFunction("+", of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + arg2.asNumber().getValue());
        })));
        context.getObjects().put("-", new LispVariableReference("-", new LispCompleteFunction("-", of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() - arg2.asNumber().getValue());
        })));
        context.getObjects().put("*", new LispVariableReference("*", new LispCompleteFunction("*", of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() * arg2.asNumber().getValue());
        })));
        context.getObjects().put("/", new LispVariableReference("/", new LispCompleteFunction("/", of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() / arg2.asNumber().getValue());
        })));
        context.getObjects().put("=", new LispVariableReference("=", new LispCompleteFunction("=", of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.get();
            arg2 = arg2.fullyDereference();
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
        context.getObjects().put("inc", new LispVariableReference("inc", new LispCompleteFunction("inc", of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
        })));
        context.getObjects().put("dec", new LispVariableReference("dec", new LispCompleteFunction("dec", of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() - 1);
        })));
        context.getObjects().put("randf", new LispVariableReference("randf", new LispCompleteFunction("randf", new ArrayList<>(), (parentContext, args) -> new LispNumberLiteral(Math.random()))));
        context.getObjects().put("randb", new LispVariableReference("randb", new LispCompleteFunction("randb", new ArrayList<>(), (parentContext, args) -> new LispBooleanLiteral(Math.random() < 0.5))));
//        context.getObjects().put("println", new LispVariableReference("println", new LispCompleteFunction("println", of("arg1")), ));
        System.out.println();
        run(context, "(= iterations 1000)");
        run(context, "(= randomSum 0)");
        run(context, "(for i 0 iterations (= randomSum (+ randomSum (randf))))");
        run(context, "randomSum");
        run(context, "(= randomSum (/ randomSum iterations))");
        run(context, "randomSum");
//        run(context, "(/ (+ (randf) (+ (randf) (+ (randf) (+ (randf) (+ (randf) (randf)))))) 6)");
//        run(context, "(= c (* 3 5))");
//        run(context, "(= d (+ 2 (inc c)))");
//        run(context, "c");
//        run(context, "d");
//        run(context, "(func name [x, y] (* x (inc y)))");
//        run(context, "name");
//        run(context, "(= name (func name [x, y] (* x (inc y))))");
//        run(context, "name");
//        run(context, "(name 4 5)");
    }

}
