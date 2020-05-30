package p0nki.espressolisp.run;

import p0nki.espressolisp.object.LispNullObject;
import p0nki.espressolisp.object.LispObject;

import java.util.HashMap;
import java.util.Map;

public class LispContext {

    private final Map<String, LispObject> objects;

    public LispContext() {
        objects = new HashMap<>();
    }

    public LispContext push() {
        LispContext ctx = new LispContext();
        for (String key : objects.keySet()) ctx.objects.put(key, objects.get(key));
        return ctx;
    }

    public Map<String, LispObject> getObjects() {
        return objects;
    }

    public LispObject get(String name) {
        if (!objects.containsKey(name)) {
            return LispNullObject.INSTANCE;
        }
        return objects.get(name);
    }

}
