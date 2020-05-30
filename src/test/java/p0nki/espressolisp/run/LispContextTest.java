package p0nki.espressolisp.run;

import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispBinaryFunctionAdapter;
import p0nki.espressolisp.function.LispUnaryFunctionAdapter;
import p0nki.espressolisp.object.LispCompleteFunction;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispNumberLiteral;
import p0nki.espressolisp.object.LispVariableReference;

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
        context.getObjects().put("inc", new LispVariableReference("inc", new LispCompleteFunction("inc", of("arg1"), (LispUnaryFunctionAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
        })));
        context.getObjects().put("dec", new LispVariableReference("dec", new LispCompleteFunction("dec", of("arg1"), (LispUnaryFunctionAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullObject.INSTANCE) return LispNullObject.INSTANCE;
            return new LispNumberLiteral(arg1.asNumber().getValue() - 1);
        })));
        System.out.println(context.evaluate("(= c (* 3 5))"));
        System.out.println(context.evaluate("(= d (+ 2 (inc c)))"));
        System.out.println(context.evaluate("c") + " = " + context.evaluate("c").fullyDereference());
        System.out.println(context.evaluate("d") + " = " + context.evaluate("d").fullyDereference());
    }

}
