package p0nki.espressolisp.run;

import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.LispVariableReference;

import java.util.HashMap;
import java.util.Map;

public class LispContext {

    private final LispContext parent;

    private final Map<String, LispVariableReference> objects;

    public LispContext(LispContext parent) {
        this.parent = parent;
        objects = new HashMap<>();
    }

    public boolean hasParent() {
        return parent != null;
    }

    public LispContext getParent() {
        return parent;
    }

    public LispContext push() {
        LispContext ctx = new LispContext(this);
        for (String key : objects.keySet()) ctx.objects.put(key, objects.get(key));
        return ctx;
    }

    public Map<String, LispVariableReference> getObjects() {
        return objects;
    }

    public LispObject get(String name) {
        if (!objects.containsKey(name)) {
            objects.put(name, new LispVariableReference(name, LispNullObject.INSTANCE));
        }
        return objects.get(name);
    }

}
