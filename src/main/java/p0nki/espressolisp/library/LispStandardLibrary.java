package p0nki.espressolisp.library;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispDyadAdapter;
import p0nki.espressolisp.function.LispMonadAdapter;
import p0nki.espressolisp.object.*;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.utils.Utils;

import java.util.ArrayList;

public class LispStandardLibrary implements LispLibrary {

    @Override
    public String getName() {
        return "std";
    }

    @Override
    public void apply(LispContext context) throws LispException {
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
                if(ctx.getParent().isPresent()) ctx.getParent().get().set(ref.getName(), ref);
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

        context.set("std.randf", new LispCompleteFunction(new ArrayList<>(), (parentContext, args) -> new LispNumberLiteral(Math.random()))).makeConstant();
        context.set("std.randb", new LispCompleteFunction(new ArrayList<>(), (parentContext, args) -> new LispBooleanLiteral(Math.random() < 0.5))).makeConstant();
        context.set("std.println", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            System.out.println("STDOUT " + arg1.fullyDereference());
            return LispNullObject.INSTANCE;
        })).makeConstant();
    }

    @Override
    public void fullImport(LispContext context) throws LispException {
        context.evaluate("(= randf std.randf)");
        context.evaluate("(= randb std.randb)");
        context.evaluate("(= println std.println)");
    }
}
