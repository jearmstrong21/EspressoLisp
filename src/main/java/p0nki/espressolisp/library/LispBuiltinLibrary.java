package p0nki.espressolisp.library;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispDyadAdapter;
import p0nki.espressolisp.function.LispMonadAdapter;
import p0nki.espressolisp.object.*;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.utils.Utils;

public enum LispBuiltinLibrary implements LispLibrary {

    INSTANCE;

    @Override
    public String getName() {
        return "builtin";
    }

    @Override
    public void load(LispContext context) throws LispException {
        context.set("+", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE || arg2 == LispNullLiteral.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() + arg2.asNumber().getValue());
        })).makeConstant();
        context.set("-", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE || arg2 == LispNullLiteral.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() - arg2.asNumber().getValue());
        })).makeConstant();
        context.set("*", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE || arg2 == LispNullLiteral.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() * arg2.asNumber().getValue());
        })).makeConstant();
        context.set("/", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE || arg2 == LispNullLiteral.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() / arg2.asNumber().getValue());
        })).makeConstant();
        context.set("=", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.get();
            arg2 = arg2.fullyDereference();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            LispReference ref = (LispReference) arg1;
            if (ctx.getParent().get().has(ref.getName())) {
                ctx.getParent().get().get(ref.getName()).set(arg2);
            } else {
                ref.set(arg2);
                if (ctx.getParent().isPresent()) ctx.getParent().get().set(ref.getName(), ref);
            }
            return arg2;
        })).makeConstant();
        context.set("inc", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE) throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() + 1);
        })).makeConstant();
        context.set("dec", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE) throw LispException.unexpectedNull(null);
            return new LispNumberLiteral(arg1.asNumber().getValue() - 1);
        })).makeConstant();
        context.set("<", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE || arg2 == LispNullLiteral.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispBooleanLiteral(arg1.asNumber().getValue() < arg2.asNumber().getValue());
        })).makeConstant();
        context.set(">", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            if (arg1 == LispNullLiteral.INSTANCE || arg2 == LispNullLiteral.INSTANCE)
                throw LispException.unexpectedNull(null);
            return new LispBooleanLiteral(arg1.asNumber().getValue() > arg2.asNumber().getValue());
        })).makeConstant();
        context.set("isconst", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            return new LispBooleanLiteral(arg1.asReference().isConstant());
        }));
        context.set("const", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            arg1.asReference().makeConstant();
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("import", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            parentContext.getParent().get().importLibrary(arg1.asReference().getName());
            return LispNullLiteral.INSTANCE;
        }));
    }

    @Override
    public void fullImport(LispContext context) throws LispException {
        context.getLogger().warn("ATTEMPTED TO IMPORT BUILTIN LIBRARY");
    }
}
