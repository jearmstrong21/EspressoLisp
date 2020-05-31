package p0nki.espressolisp.run;

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
        LispContext context = new LispContext(new LispStandardLogger(), null);
        context.potentialLibrary(LispStandardLibrary.INSTANCE);
        context.potentialLibrary(LispMathLibrary.INSTANCE);
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

//        run(context, "println");
//        run(context, "std.println");
//        run(context, "(applylib std)");
//        run(context, "println");
//        run(context, "std.println");
//        run(context, "(importlib std)");
//        run(context, "println");
//        run(context, "std.println");
//
        run(context, "(applylib std)");
        run(context, "(= factorial (func [n] (if (< n 2) 1 (* n (factorial (dec n))))))");
        run(context, "(for (= i 1) (< i 11) (= i (inc i)) (std.println (factorial i)))");
        run(context, "'hi'");

//        run(context, "arg1");
//        run(context, "(= arg1 5)");
//        run(context, "arg1");

        // TODO test `while` and `for` again, they have been rewritten


    }

}
