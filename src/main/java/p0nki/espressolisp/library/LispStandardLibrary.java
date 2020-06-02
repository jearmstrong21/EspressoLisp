package p0nki.espressolisp.library;

import p0nki.espressolisp.adapter.LispMonadAdapter;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.*;
import p0nki.espressolisp.object.reference.LispReference;
import p0nki.espressolisp.object.reference.LispStandardReferenceImpl;
import p0nki.espressolisp.run.LispContext;
import p0nki.espressolisp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum LispStandardLibrary implements LispLibrary {

    INSTANCE;

    @Override
    public String getName() {
        return "std";
    }

    @Override
    public void load(LispContext ctx) throws LispException {
        Map<String, LispObject> std = new HashMap<>();
        std.put("randf", new LispReference("randf", true, new LispStandardReferenceImpl(new LispCompleteFunctionLiteral(new ArrayList<>(), (parentContext, args) -> new LispNumberLiteral(Math.random())))));
        std.put("randb", new LispReference("randb", true, new LispStandardReferenceImpl(new LispCompleteFunctionLiteral(new ArrayList<>(), (parentContext, args) -> new LispBooleanLiteral(Math.random() < 0.5)))));
        std.put("println", new LispReference("println", true, new LispStandardReferenceImpl(new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            parentContext.getLogger().out(arg1);
            return LispNullLiteral.INSTANCE;
        }))));
        std.put("warnln", new LispReference("warnln", true, new LispStandardReferenceImpl(new LispCompleteFunctionLiteral(Utils.of("arg1"), (LispMonadAdapter) (parentContext, arg1) -> {
            parentContext.getLogger().warn(arg1);
            return LispNullLiteral.INSTANCE;
        }))));
        ctx.set("std", new LispMapLiteral(std)).makeConstant();
    }

}
