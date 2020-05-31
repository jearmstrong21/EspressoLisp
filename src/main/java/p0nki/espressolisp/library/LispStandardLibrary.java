package p0nki.espressolisp.library;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.function.LispMonadAdapter;
import p0nki.espressolisp.object.*;
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
        context.set("std.randf", new LispCompleteFunction(new ArrayList<>(), (parentContext, args) -> new LispNumberLiteral(Math.random()))).makeConstant();
        context.set("std.randb", new LispCompleteFunction(new ArrayList<>(), (parentContext, args) -> new LispBooleanLiteral(Math.random() < 0.5))).makeConstant();
        context.set("std.println", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            parentContext.getLogger().out(arg1);
            return LispNullObject.INSTANCE;
        })).makeConstant();
        context.set("std.warnln", new LispCompleteFunction(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            parentContext.getLogger().warn(arg1);
            return LispNullObject.INSTANCE;
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
