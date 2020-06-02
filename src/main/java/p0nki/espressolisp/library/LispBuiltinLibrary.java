package p0nki.espressolisp.library;

import p0nki.espressolisp.adapter.LispDyadAdapter;
import p0nki.espressolisp.adapter.LispMonadAdapter;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.*;
import p0nki.espressolisp.object.reference.LispReference;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.utils.Utils;

import java.util.List;
import java.util.Map;

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
            LispReference ref = arg1.asReference();
            ref.set(arg2);
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
        context.set("const", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            arg1.asReference().makeConstant();
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("import", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
//            arg1 = arg1.get();
//            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
//            ctx.getParent().get().importLibrary(arg1.asReference().getName());
            if (!ctx.getParent().isPresent()) throw LispException.noParentContext(null);
            String str = arg1.fullyDereference().asString().getValue();
            ctx.getParent().get().importLibrary(str);
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("concat", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            arg1 = arg1.fullyDereference();
            arg2 = arg2.fullyDereference();
            return new LispStringLiteral(arg1.asString().getValue() + arg2.asString().getValue());
        })).makeConstant();
        context.set("substr", new LispCompleteFunctionLiteral(Utils.of("string", "start", "end"), (ctx, args) -> {
            if (args.size() != 3) throw LispException.invalidArgList(3, args.size(), null);
            return new LispStringLiteral(
                    args.get(0).fullyDereference().asString().getValue().substring(
                            (int) args.get(1).fullyDereference().asNumber().assertInteger().getValue(),
                            (int) args.get(2).fullyDereference().asNumber().assertInteger().getValue()));
        })).makeConstant();
        context.set("del", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            arg1 = arg1.get();
            if (!arg1.isLValue()) throw LispException.invalidValueType(true, false, null);
            if (!ctx.getParent().isPresent()) throw LispException.noParentContext(null);
            arg1.asReference().delete();
            return LispNullLiteral.INSTANCE;
        })).makeConstant();

        context.set("nth", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            final LispObject reference = arg1.get();
            List<LispObject> objects = arg1.fullyDereference().asList().getObjects();
            int index = (int) arg2.fullyDereference().asNumber().assertInteger().getValue();
            if (-objects.size() < index && index < 0) index += objects.size();
            int finalIndex = index;
            return new LispReference("list[" + index + "]", false, new LispReference.Impl() {
                @Override
                public void set(LispObject newValue) throws LispException {
                    if (reference.isLValue()) {
                        objects.set(finalIndex, newValue);
                        reference.asReference().set(new LispListLiteral(objects));
                    } else {
                        throw new LispException("Cannot set rvalue", null);
                    }
                }

                @Override
                public LispObject get() {
                    return objects.get(finalIndex);
                }

                @Override
                public void delete() throws LispException {
                    if (reference.isLValue()) {
                        objects.remove(finalIndex);
                        reference.asReference().set(new LispListLiteral(objects));
                    } else {
                        throw new LispException("Cannot delete rvalue", null);
                    }
                }
            });
        })).makeConstant();
        context.set(".", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            final LispObject reference = arg1.get();
            Map<String, LispObject> objects = arg1.fullyDereference().asMap().getObjects();
            String key = arg2.fullyDereference().asString().getValue();
            return new LispReference("map[" + key + "]", false, new LispReference.Impl() {
                @Override
                public void set(LispObject newValue) throws LispException {
                    if (reference.isLValue()) {
                        objects.put(key, newValue);
                        reference.asReference().set(new LispMapLiteral(objects));
                    } else {
                        throw new LispException("Cannot set rvalue", null);
                    }
                }

                @Override
                public LispObject get() {
                    return objects.get(key);
                }

                @Override
                public void delete() throws LispException {
                    if (reference.isLValue()) {
                        objects.remove(key);
                        reference.asReference().set(new LispMapLiteral(objects));
                    } else {
                        throw new LispException("Cannot delete rvalue", null);
                    }
                }
            });
        })).makeConstant();
        context.set("set", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2", "arg3"), (ctx, args) -> {
            LispListLiteral arg1 = args.get(0).fullyDereference().asList();
            LispObject arg2 = args.get(1).fullyDereference();
            LispNumberLiteral arg3 = args.get(3).fullyDereference().asNumber().assertInteger();
            arg1.getObjects().set((int) arg3.getValue(), arg2);
            return LispNullLiteral.INSTANCE;
        }));
        context.set("push", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> {
            LispListLiteral list = arg1.fullyDereference().asList();
            LispObject toPush = arg2.fullyDereference();
            List<LispObject> values = list.deepCopy().asList().getObjects();
            values.add(toPush);
            return new LispListLiteral(values);
        })).makeConstant();
        context.set("pop", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> {
            LispListLiteral list = arg1.fullyDereference().asList();
            List<LispObject> values = list.deepCopy().asList().getObjects();
            values.remove(values.size() - 1);
            return new LispListLiteral(values);
        })).makeConstant();

        context.set("and", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> new LispBooleanLiteral(arg1.fullyDereference().asBoolean().getValue() && arg2.fullyDereference().asBoolean().getValue()))).makeConstant();
        context.set("or", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> new LispBooleanLiteral(arg1.fullyDereference().asBoolean().getValue() || arg2.fullyDereference().asBoolean().getValue()))).makeConstant();
        context.set("xor", new LispCompleteFunctionLiteral(Utils.of("arg1", "arg2"), (LispDyadAdapter) (ctx, arg1, arg2) -> new LispBooleanLiteral(arg1.fullyDereference().asBoolean().getValue() ^ arg2.fullyDereference().asBoolean().getValue()))).makeConstant();
        // TODO insert  remove for lists
        // TODO for `nth` support negatives
        // TODO sublist

        context.set("copy", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> arg1.fullyDereference().deepCopy())).makeConstant();
        context.set("str", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> new LispStringLiteral(arg1.fullyDereference().lispStr()))).makeConstant();
        context.set("len", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> new LispNumberLiteral(arg1.fullyDereference().lispLen()))).makeConstant();
        context.set("typeof", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> new LispStringLiteral(arg1.fullyDereference().getType()))).makeConstant();

        //TODO figure out if this should be deleted. DO NOT RESOLVE TODO UNTIL THIS IS EITHER UNCOMMENTED OR DELETED
//        context.set("deref", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (ctx, arg1) -> arg1.fullyDereference())).makeConstant();
    }

    @Override
    public void fullImport(LispContext context) {
        context.getLogger().warn("ATTEMPTED TO IMPORT BUILTIN LIBRARY");
    }
}
