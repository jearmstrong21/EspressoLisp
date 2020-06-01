package p0nki.espressolisp.run;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.library.LispMathLibrary;
import p0nki.espressolisp.library.LispStandardLibrary;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenizer;
import p0nki.espressolisp.tree.LispASTCreator;
import p0nki.espressolisp.tree.LispTreeNode;
import p0nki.espressolisp.utils.LispStandardLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class LispContextTest {

    private void run(LispContext context, String code) {
        List<LispToken> tokens = LispTokenizer.tokenize(code);
        List<LispToken> originalTokens = new ArrayList<>(tokens);
        System.out.println(code);
        while (tokens.size() > 0) {
            try {
                LispTreeNode tree = LispASTCreator.parse(tokens);
//                System.out.println(tree.toDebugJSON().toString(5));
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
                    System.out.println(originalTokens.stream().map(LispToken::toString).collect(Collectors.joining("\n")));
                }
                exc.printStackTrace();
                System.exit(1);
            }
        }
        System.out.println();
    }

    private static LispContext create() throws LispException {
        LispContext ctx = new LispContext(new LispStandardLogger(), null);
        ctx.potentialLibrary(LispStandardLibrary.INSTANCE);
        ctx.potentialLibrary(LispMathLibrary.INSTANCE);
        ctx.evaluate("(import 'std')");
        return ctx;
    }

    @Test(timeout = 100)
    public void testInlineFunctions() throws LispException {
        LispContext ctx = create();
        Assert.assertEquals(7.0, ctx.evaluate("((func [x] (+ 2 x)) 5)").asNumber().getValue(), 0);
        ctx.evaluate("(= consume (func [x] (std.println (concat 'consumed ' (str x)))))");
        ctx.evaluate("(= which (func [x] (if (< x 0.5) std.println consume)))");
        Assert.assertEquals("std.println", ctx.evaluate("(which 0.3)").asReference().getName());
        Assert.assertEquals("consume", ctx.evaluate("(which 0.7)").asReference().getName());
    }

    @Test(timeout = 100)
    public void testFor() throws LispException {
        LispContext ctx = create();
        ctx.evaluate("(= product 1)");
        ctx.evaluate("(for (= i 1) (< i 9) (= i (inc i)) (= product (* product i)))");
        Assert.assertEquals(2 * 3 * 4 * 5 * 6 * 7 * 8, (int) ctx.evaluate("product").fullyDereference().asNumber().assertInteger().getValue());
    }

    @Test(timeout = 100)
    public void testWhile() throws LispException {
        LispContext ctx = create();
        ctx.evaluate("(= i 1)");
        ctx.evaluate("(while (< i 10) (= i (inc i)))");
        Assert.assertEquals(10, (int) ctx.evaluate("i").fullyDereference().asNumber().assertInteger().getValue());
    }

    @Test(timeout = 100)
    public void testDo() throws LispException {
        LispContext ctx = create();
        Assert.assertEquals(20, (int) ctx.evaluate("(do (= i 5) (while (< i 20) (= i (+ 5 i))) i)").fullyDereference().asNumber().assertInteger().getValue());
    }

    @Test(timeout = 100
            , expected = LispException.class
    )
    public void testConst() throws LispException {
        LispContext ctx = create();
        ctx.evaluate("(= i 5)");
        Assert.assertEquals(5, (int) ctx.evaluate("i").fullyDereference().asNumber().assertInteger().getValue());
        ctx.evaluate("(const i)");
        Assert.assertTrue(ctx.evaluate("(isconst i)").asBoolean().getValue());
        ctx.evaluate("(= i 3)");
    }

    @Test(timeout = 100)
    public void testList() throws LispException {
        LispContext ctx = create();
        ctx.evaluate("(= l (list 5 4 3 2 1 0))");
        Assert.assertEquals(4, (int) ctx.evaluate("(nth l 1)").fullyDereference().asNumber().assertInteger().getValue());
        Assert.assertEquals(6, (int) ctx.evaluate("(len l)").asNumber().assertInteger().getValue());
        Assert.assertEquals(7, (int) ctx.evaluate("(len (push l (list 3 4)))").asNumber().assertInteger().getValue());
        Assert.assertEquals(5, (int) ctx.evaluate("(len (pop l))").asNumber().assertInteger().getValue());
        Assert.assertEquals(3, (int) ctx.evaluate("(nth (push l 3) -1)").fullyDereference().asNumber().assertInteger().getValue());
//        run(ctx, "(= foo (func [x] (foo x)))");
//        run(ctx, "(foo 5)");
//        run(ctx, "(= x 3)");
//        run(ctx, "(+ x 4)");
//        run(ctx, "(= my_list (list 3 4 5))");
//        run(ctx, "my_list");
//        run(ctx, "(nth my_list 0)");
//        run(ctx, "(= (nth my_list 0) 6)");
//        run(ctx, "my_list");
//        run(ctx, "(nth (list 3 4 5) 1)");
//        run(ctx, "(= (nth (list 3 4 5) 1) 10)");
//        run(ctx, "x");
//        System.out.println("hi");
    }

//    @Test(timeout = 100)
//    public void test() throws LispException, JSONException {
//        LispContext context = new LispContext(new LispStandardLogger(), null);
//        context.potentialLibrary(LispStandardLibrary.INSTANCE);
//        context.potentialLibrary(LispMathLibrary.INSTANCE);
//        System.out.println();
//        System.out.println("BUILTIN SYMBOLS");
//        System.out.println(context.keys());
//        System.out.println();
//        System.out.println();

    //`del` test
//        run(context, "(= x 5)");
//        run(context, "x");
//        run(context, "(del x)");
//        run(context, "x");

//        run(context, "(concat 'alfalfa 'p0nki)");
//        run(context, "(concat 'alfalfa' 'p0nki')");

//        run(context, "(= x 5)");
//        run(context, "x");
//        run(context, "(= x 3)");
//        run(context, "x");
//        run(context, "(const x)");
//        run(context, "x");
//        run(context, "(if (isconst x) 1 0)");
//        run(context, "(std.println (if (isconst x) 1 0))");

//        run(context, "null");
//        run(context, "NaN");
//        run(context, "(+ 5 NaN)");
//        run(context, "(+ 5 null)");

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

    // TODO: switch applylib and importlib
    //  make them take string literals and thus be `CompleteFunction`s in BuiltinLibrary
    //  actually make string literals lol
    //  make println take multiple arguments? variadic arguments is not possible with FunctionInvokeNode checking against a single argument count, add IntPredicate?
    //  ^^^^ wait for lists
    //  0) DELETE HACKY LIB PARSING CODE FOR NOW, REPL IS ENOUGH TO TEST THE FOLLOWING
    //  1) CHAR LITERALS AND STRING LITERALS FIRST AND FOREMOST THEN FIX LIBRARY LOADING
    //  2) CHAR AND STRING LITERALS REQUIRE TOKENIZER INTERVENTION TO PREVENT SPACE DELIMITING, THATS THE WHOLE POINT OF THE TOKENIZER
    //  3) AFTTER CHAR/STRING LITERALS AND PARSING, LISTS
    //  4) THEN AFTER LISTS MAKE COMPOUND OBJECTS
    //  5) MAKE STANDARD LIBRARY A COMPOUND OBJECT
    //  6) VERY IMPORTANT AUTOMATE FULLIMPORT ON LIBRARY TO """MERGE""" LIBRARY WITH LOCAL CONTEXT. HOW TO DEAL WITH CONFLICTS? REPLACE WITH LIB, REPLACE WITH LOCAL, OR THROW? THROW IS PROBABLY BEST IDEA
    //  7) CHAR AND STRING LITERALS SHOULD NOT BE DISTINCT!
    //  8) ADD QUOTED_LITERAL AS WELL, MODIFY AST PARSER TO BE "PARSELITERAL" INSTEAD OF "PARSEUNQUOTEDLITERAL"
    //  9) STRING OPERATIONS, REFACTOR AND RENAME EVERY SINGLE NAME

//        run(context, "std.println");
//        run(context, "(import std)");
//        run(context, "std.println");
//
//        run(context, "(import std)");
//        run(context, "(= factorial (func [n] (if (< n 2) 1 (* n (factorial (dec n))))))");
//        run(context, "(factorial (+ 5 3))");
//        run(context, "(for (= i 1) (< i 11) (= i (inc i)) (std.println (factorial i)))");
//        run(context, "(= fib (func [n] (if (< n 2) 1 (+ (fib (- n 1)) (fib (- n 2))))))");
//        run(context, "(for (= i 1) (< i 11) (= i (inc i)) (std.println (fib i)))");

//        run(context, "(= sum (func [x y] (if (< x 2) 1 (if (< y 2) 1 (+ x (+ y (+ (sum (dec x) y) (sum x (dec y)))))))))");
//        run(context, "(sum 5 5)");

//        run(context, "(= test (list 5 4 3 2 1))");
//        run(context, "test");
//        run(context, "(typeof test)");
//        run(context, "(nth test 3)");
//        run(context, "(= i 2)");
//        run(context, "(nth test i)");
//        run(context, "(nth (list 0 1 2 3) (+ 1 2))");

//        run(context, "(= a (list 1 (list 2 3)))");
//        run(context, "a");
//        run(context, "(= b (push a (list 4 5)))");
//        run(context, "a");
//        run(context, "b");
//        run(context, "(pop a)");
//        run(context, "a");
//        run(context, "(= a (pop a))");
//        run(context, "a");

//        run(context, "(concat 'hi' (concat ' ' 'world'))");
//        run(context, "(concat 'hi' 'world')");
//        run(context, "(concat 'hi' (str 5))");
//        run(context, "(substr 'hello world' 6)");
//        run(context, "(substr 'hello world' 4 (+ 2 5))");
//        run(context, "(do (for (do (= i 1) (= j 0)) (< i 21) (= i (inc i)) (= j (+ j (* i i)))) j)");

//        run(context, "((func [x] (+ 3 x)) 5)");
//        run(context, "(= f (func [x] (+ 2 x)))");
//        run(context, "(f 5)");
//        run(context, "x");
//        run(context, "3");
//        run(context, "true");
//        run(context, "null");

//        run(context, "(do (= f (func [x] (+ 2 x))) (f 5))");
//        run(context, "f");

    // TODO test `while` and `for` again, they have been rewritten


//    }

}
