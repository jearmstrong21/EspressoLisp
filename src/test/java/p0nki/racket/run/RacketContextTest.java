package p0nki.racket.run;

import org.junit.Test;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketCompleteFunction;
import p0nki.racket.object.RacketFunction;
import p0nki.racket.object.RacketNumericLiteral;
import p0nki.racket.object.RacketObject;
import p0nki.racket.token.RacketToken;
import p0nki.racket.token.RacketTokenizer;
import p0nki.racket.tree.RacketASTCreator;
import p0nki.racket.tree.RacketTreeNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RacketContextTest {

    private static List<String> of(String a, String b) {
        List<String> l = new ArrayList<>();
        l.add(a);
        l.add(b);
        return l;
    }

    @Test
    public void test() throws RacketException {
        RacketContext context = new RacketContext();
        context.getObjects().put("+", new RacketCompleteFunction("+", of("arg1","arg2"),args -> {

        }));
//        context.getObjects().put("+", new RacketFunction("+", of("arg1", "arg2"), new RacketTreeNode() {
//            @Override
//            public RacketObject evaluate(RacketContext context) throws RacketException {
//                RacketNumericLiteral arg1 = context.get("arg1").asNumericLiteral();
//                RacketNumericLiteral arg2 = context.get("arg2").asNumericLiteral();
//                return new RacketNumericLiteral(arg1.getValue().add(arg2.getValue()));
//            }
//
//            @Override
//            public String debugStringify(String indent) {
//                return indent + "+";
//            }
//        }));
//        context.getObjects().put("*", new RacketFunction("*", of("arg1", "arg2"), new RacketTreeNode() {
//            @Override
//            public RacketObject evaluate(RacketContext context) throws RacketException {
//                RacketNumericLiteral arg1 = context.get("arg1").asNumericLiteral();
//                RacketNumericLiteral arg2 = context.get("arg2").asNumericLiteral();
//                return new RacketNumericLiteral(arg1.getValue().multiply(arg2.getValue()));
//            }
//
//            @Override
//            public String debugStringify(String indent) {
//                return indent + "*";
//            }
//        }));
        String code = "(+ 4 (* 5 6))";
        List<RacketToken> tokens = RacketTokenizer.tokenize(code);
        RacketTreeNode ast = RacketASTCreator.parse(tokens);
        RacketObject result = ast.evaluate(context);
        System.out.println(ast.debugStringify(""));
        System.out.println(context.getObjects().keySet());
        System.out.println(result);
    }

}
