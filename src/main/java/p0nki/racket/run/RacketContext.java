package p0nki.racket.run;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketNullObject;
import p0nki.racket.object.RacketObject;

import java.util.HashMap;
import java.util.Map;

public class RacketContext {

    private final Map<String, RacketObject> objects;

    public RacketContext() {
        objects = new HashMap<>();
    }

    public RacketContext push() {
        RacketContext ctx = new RacketContext();
        for (String key : objects.keySet()) ctx.objects.put(key, objects.get(key));
        return ctx;
    }

    public Map<String, RacketObject> getObjects() {
        return objects;
    }

    public RacketObject get(String name) {
        if (!objects.containsKey(name)) {
            return RacketNullObject.INSTANCE;
        }
        return objects.get(name);
    }

}
