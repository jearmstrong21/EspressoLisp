package p0nki.espressolisp.library;

import p0nki.espressolisp.adapter.LispDyadAdapter;
import p0nki.espressolisp.adapter.LispMonadAdapter;
import p0nki.espressolisp.exceptions.LispException;
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
        })).makeConstant();
        context.set("const", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            arg1.asReference().makeConstant();
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("import", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            if (!parentContext.getParent().isPresent()) throw LispException.noParentContext(null);
            parentContext.getParent().get().importLibrary(arg1.asReference().getName());
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("str", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> new LispStringLiteral(arg1.fullyDereference().lispStr()))).makeConstant();
        context.set("concat", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (parentContext, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            return new LispStringLiteral(arg1.asString().getValue() + arg2.asString().getValue());
        })).makeConstant();
        context.set("substr", new LispCompleteFunctionLiteral(Utils.of("string", "start", "end"), (parentContext, args) -> {
            if (args.size() == 0) throw LispException.invalidArgList(2, 0, null);
            if (args.size() == 1) throw LispException.invalidArgList(2, 1, null);
            LispStringLiteral str = args.get(0).fullyDereference().asString();
            LispNumberLiteral start = args.get(1).fullyDereference().asNumber().assertInteger();
            if (args.size() == 2) {
                return new LispStringLiteral(str.getValue().substring((int) start.getValue()));
            }
            LispObject arg3 = args.get(2).fullyDereference();
            if (arg3 == LispNullLiteral.INSTANCE)
                return new LispStringLiteral(str.getValue().substring((int) start.getValue()));
            LispNumberLiteral end = args.get(2).fullyDereference().asNumber().assertInteger();
            return new LispStringLiteral(str.getValue().substring((int) start.getValue(), (int) end.getValue()));
        })).makeConstant();
        context.set("len", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> new LispNumberLiteral(arg1.asString().getValue().length()))).makeConstant();
        context.set("typeof", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> new LispStringLiteral(arg1.fullyDereference().getType()))).makeConstant();
    }

    @Override
    public void fullImport(LispContext context) {
        context.getLogger().warn("ATTEMPTED TO IMPORT BUILTIN LIBRARY");
    }
}
