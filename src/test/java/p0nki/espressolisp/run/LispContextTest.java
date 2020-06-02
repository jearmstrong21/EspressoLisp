package p0nki.espressolisp.run;

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
                LispObject res = tree.evaluate(context);
                System.out.print("=> " + res.lispStr());
                while (res.isLValue()) {
                    res = res.get();
                    System.out.print(" = " + res.lispStr());
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

    @Test(timeout = 100, expected = LispException.class)
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
    }

    @Test(timeout = 100)
    public void testMap() throws LispException {
        LispContext ctx = create();
        ctx.evaluate("(= x (map 'a' 5 'c' 2))");
        Assert.assertEquals(5, (int) ctx.evaluate("(. x 'a')").fullyDereference().asNumber().assertInteger().getValue());
        ctx.evaluate("(= (. x 'b') (func [x] (* x x)))");
        Assert.assertEquals(36, (int) ctx.evaluate("((. x 'b') 6)").fullyDereference().asNumber().assertInteger().getValue());
    }

    @Test(timeout = 100, expected = LispException.class)
    public void testDeleteInlineMap() throws LispException {
        create().evaluate("(del (. (map 'a' 5 'b' 2) 'a'))");
    }

    @Test(timeout = 100, expected = LispException.class)
    public void testAssignInlineMap() throws LispException {
        create().evaluate("(= (. (map 'a' 5 'b' 2) 'a') 10)");
    }

    @Test(timeout = 100, expected = LispException.class)
    public void testDeleteInlineList() throws LispException {
        create().evaluate("(del (nth (list 1 2 3 4) 2))");
    }

    @Test(timeout = 100, expected = LispException.class)
    public void testAssignInlineList() throws LispException {
        create().evaluate("(= (nth (list 1 2 3 4) 2) 5)");
    }

    private void boolOp(LispContext ctx, String code, boolean res) throws LispException {
        Assert.assertEquals(res, ctx.evaluate(code).fullyDereference().asBoolean().getValue());
    }

    @Test
    public void testBooleanOps() throws LispException {
        LispContext ctx = create();

        boolOp(ctx, "(or true false)", true);
        boolOp(ctx, "(or false true)", true);
        boolOp(ctx, "(or true true)", true);
        boolOp(ctx, "(or false false)", false);

        boolOp(ctx, "(and true true)", true);
        boolOp(ctx, "(and true false)", false);
        boolOp(ctx, "(and false true)", false);
        boolOp(ctx, "(and false false)", false);

        boolOp(ctx, "(xor true true)", false);
        boolOp(ctx, "(xor true false)", true);
        boolOp(ctx, "(xor false true)", true);
        boolOp(ctx, "(xor false false)", false);
    }

    @Test(timeout = 100)
    public void testBoundInvoke() throws LispException {
        LispContext ctx = create();
//        run(ctx, "(= object (map 'counter' 0 'run' (func [self] (do (std.warnln (concat '[PROG] self = ' (str self)) (std.warnln (concat '[PROG] arg1 = ' (str arg1)) (std.warnln (concat '[PROG] self.counter = ' 'hello world'))))");
        run(ctx, "(= object (map 'counter' 0 'run' (func [self] (do (= (. self 'counter') (inc (. self 'counter'))) 'hello world'))))");
        run(ctx, "object");
        run(ctx, "(:: object 'run')");
        run(ctx, "object");
    }


}
