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
import p0nki.espressolisp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class LispContextTest {

    private void run(LispContext context, String code) {
        List<LispToken> tokens = LispTokenizer.tokenize(code);
        System.out.println(code);
        while (tokens.size() > 0) {
            try {
                LispTreeNode tree = LispASTCreator.parse(tokens);
//                System.out.println(tree.debugStringify(""));
                LispObject res = tree.evaluate(context);
                System.out.print("=> " + res);
                while (res.isLValue()) {
                    res = res.get();
                    System.out.print(" = " + res);
                }
                System.out.println();
            } catch (LispException exc) {
                if (exc.getToken() == null) {
                    System.out.println("-- Runtime error --");
                    System.out.println(exc.getMessage());
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
        }
        System.out.println();
    }

    @Test
    public void test() throws LispException {
        LispContext context = new LispContext(null);
        context.set("+", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() + arg2.asNumber().getValue());
        })).makeConstant();
        context.set("-", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() - arg2.asNumber().getValue());
        })).makeConstant();
        context.set("*", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() * arg2.asNumber().getValue());
        })).makeConstant();
        context.set("/", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() / arg2.asNumber().getValue());
        })).makeConstant();
        context.set("=", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.get();
            arg2 = arg2.fullyDereference();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            LispVariableReference ref = (LispVariableReference) arg1;
            if (ctx.has(ref.getName())) {
                ctx.get(ref.getName()).set(arg2);
            } else {
                ref.set(arg2);
                if (ctx.getParent().isPresent()) ctx.getParent().get().set(ref.getName(), ref);
            }
            return arg2;
        })).makeConstant();
        context.set("inc", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE) throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
        })).makeConstant();
        context.set("dec", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE) throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() - 1);
        })).makeConstant();
        context.set("<", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispBooleanLiteral(arg1.asNumber().getValue() < arg2.asNumber().getValue());
        })).makeConstant();
        context.set(">", new LispCompleteFunction(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE || arg2 == LispNullObject.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispBooleanLiteral(arg1.asNumber().getValue() > arg2.asNumber().getValue());
        })).makeConstant();
        context.set("isconst", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            return new LispBooleanLiteral(((LispVariableReference) arg1).isConstant());
        }));

        context.set("std.randf", new LispCompleteFunction(new ArrayList<>(), (parentContext, args) -> new LispNumberLiteral(Math.random()))).makeConstant();
        context.set("std.randb", new LispCompleteFunction(new ArrayList<>(), (parentContext, args) -> new LispBooleanLiteral(Math.random() < 0.5))).makeConstant();
        context.set("std.println", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            System.out.println("STDOUT " + arg1.fullyDereference());
            return LispNullObject.INSTANCE;
        })).makeConstant();
        System.out.println();

        //`del` test
//        run(context, "(= x 5)");
//        run(context, "x");
//        run(context, "(del x)");
//        run(context, "x");
//        run(context, "y");
//        run(context, "(= null x)");

//        run(context, "(= x 5)");
//        run(context, "x");
//        run(context, "(= x 3)");
//        run(context, "x");
//        run(context, "(const x)");
//        run(context, "x");
//        run(context, "(if (isconst x) 1 0)");
//        run(context, "(std.println (if (isconst x) 1 0))");

//        run(context, "(= iterations 1000)");
//        run(context, "(const iterations)");
//        run(context, "(= sum 0)");
//        run(context, "(= count (/ iterations 2))");
//        run(context, "(for (= i 0) (< i iterations) (= i (inc i)) (= sum (+ sum (std.randf))))");
//        run(context, "(for (= i 0) (< i iterations) (= i (inc i)) (if (< (std.randf) 0.5) (= count (inc count)) (= count (dec count))))");
//        run(context, "(std.println iterations)");
//        run(context, "(std.println sum)");
//        run(context, "(std.println (/ sum iterations))");
//        run(context, "(std.println count)");

        run(context, "(= factorial (func [n] (if (< n 2) 1 (* n (factorial (dec n))))))");
        run(context, "(for (= i 1) (< i 11) (= i (inc i)) (std.println (factorial i)))");

        // TODO test `while` and `for` again, they have been rewritten


    }

}
