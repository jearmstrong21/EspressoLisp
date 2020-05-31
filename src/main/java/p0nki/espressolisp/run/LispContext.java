package p0nki.espressolisp.run;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.LispVariableReference;
import p0nki.espressolisp.token.LispTokenizer;
import p0nki.espressolisp.tree.LispASTCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LispContext {

    private final LispContext parent;

    private final Map<String, LispVariableReference> objects;

    public LispContext(LispContext parent) {
        this.parent = parent;
        objects = new HashMap<>();
    }

    public void overwrite(String name, LispVariableReference ref){
        objects.put(name, ref);
    }

    public LispVariableReference set(String name, LispObject obj) throws LispException {
        get(name).set(obj);
        return get(name);
    }

    public void delete(String name) throws LispException {
        if (!objects.containsKey(name)) throw LispException.unknownVariable(name, null);
        objects.remove(name);
    }

    //    public boolean hasParent() {
//        return parent != null;
//    }
//
//    public LispContext getParent() {
//        return parent;
//    }
    public Optional<LispContext> getParent() {
        return Optional.ofNullable(parent);
    }

    public boolean has(String name) {
        return objects.containsKey(name);
    }

    public LispContext push() {
        LispContext ctx = new LispContext(this);
        for (String key : objects.keySet()) ctx.objects.put(key, objects.get(key));
        return ctx;
    }

    public LispVariableReference get(String name) {
        if (!objects.containsKey(name)) {
            objects.put(name, new LispVariableReference(name, false, LispNullObject.INSTANCE));
        }
        return objects.get(name);
    }

    public LispObject evaluate(String code) throws LispException {
        return LispASTCreator.parse(LispTokenizer.tokenize(code)).evaluate(this);
    }

}
