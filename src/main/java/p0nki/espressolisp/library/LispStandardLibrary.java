package p0nki.espressolisp.library;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.adapter.LispMonadAdapter;
import p0nki.espressolisp.object.literal.LispBooleanLiteral;
import p0nki.espressolisp.object.literal.LispCompleteFunctionLiteral;
import p0nki.espressolisp.object.literal.LispNullLiteral;
import p0nki.espressolisp.object.literal.LispNumberLiteral;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.utils.Utils;

import java.util.ArrayList;

public enum LispStandardLibrary implements LispLibrary {

    INSTANCE;

    @Override
    public String getName() {
        return "std";
    }

    @Override
    public void load(LispContext context) throws LispException {
        context.set("std.randf", new LispCompleteFunctionLiteral(new ArrayList<>(), (parentContext, args) -> new LispNumberLiteral(Math.random()))).makeConstant();
        context.set("std.randb", new LispCompleteFunctionLiteral(new ArrayList<>(), (parentContext, args) -> new LispBooleanLiteral(Math.random() < 0.5))).makeConstant();
        context.set("std.println", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            parentContext.getLogger().out(arg1);
            return LispNullLiteral.INSTANCE;
        })).makeConstant();
        context.set("std.warnln", new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            parentContext.getLogger().warn(arg1);
            return LispNullLiteral.INSTANCE;
        }));
    }

    @Override
    public void fullImport(LispContext context) throws LispException {
        context.set("randf", context.get("std.randf")).makeConstant();
        context.set("randb", context.get("std.randb")).makeConstant();
        context.set("println", context.get("std.println")).makeConstant();
        context.set("warnln", context.get("std.warnln")).makeConstant();
    }
}
