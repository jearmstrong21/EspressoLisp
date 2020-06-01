package p0nki.espressolisp.library;

import p0nki.espressolisp.adapter.LispDyadAdapter;
import p0nki.espressolisp.adapter.LispMonadAdapter;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.LispReference;
import p0nki.espressolisp.object.literal.*;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.utils.Utils;

import java.util.List;

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
            return LispNullLiteral.INSTANCE;
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
            if (args.size() != 3) throw LispException.invalidArgList(3, args.size(), null);
            return new LispStringLiteral(
                    args.get(0).fullyDereference().asString().getValue().substring(
                            (int) args.get(1).fullyDereference().asNumber().assertInteger().getValue(),
                            (int) args.get(2).fullyDereference().asNumber().assertInteger().getValue()));
        })).makeConstant();
        context.set("len", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> new LispNumberLiteral(arg1.asString().getValue().length()))).makeConstant();
        context.set("typeof", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> new LispStringLiteral(arg1.fullyDereference().getType()))).makeConstant();
        context.set("del", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            if (!parentContext.getParent().isPresent()) throw LispException.noParentContext(null);
            parentContext.getParent().get().delete(arg1.asReference().getName());
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("nth", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (parentContext, arg1, arg2) -> arg1.fullyDereference().asList().getObjects().get((int) arg2.fullyDereference().asNumber().assertInteger().getValue()))).makeConstant();
        context.set("push", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (parentContext, arg1, arg2) -> {
            LispListLiteral list = arg1.fullyDereference().asList();
            LispObject toPush = arg2.fullyDereference();
            List<LispObject> values = list.deepCopy().asList().getObjects();
            values.add(toPush);
            return new LispListLiteral(values);
        })).makeConstant();
        context.set("pop", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (parentContext, arg1, arg2) -> {
            LispListLiteral list = arg1.fullyDereference().asList();
            List<LispObject> values = list.deepCopy().asList().getObjects();
            values.remove(values.size() - 1);
            return new LispListLiteral(values);
        })).makeConstant();
        // TODO insert and remove for lists

        context.set("copy", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> arg1.fullyDereference().deepCopy())).makeConstant();
    }

    @Override
    public void fullImport(LispContext context) {
        context.getLogger().warn("ATTEMPTED TO IMPORT BUILTIN LIBRARY");
    }
}
