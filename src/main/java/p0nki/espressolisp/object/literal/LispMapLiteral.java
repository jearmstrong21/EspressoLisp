package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

import java.util.HashMap;
import java.util.Map;

public class LispMapLiteral extends LispLiteral {

    private final Map<String, LispObject> objects;

    public Map<String, LispObject> getObjects() {
        return objects;
    }

    public LispMapLiteral(Map<String, LispObject> objects) {
        this.objects = objects;
    }

    @Override
    public LispObject deepCopy() throws LispException {
        Map<String, LispObject> map = new HashMap<>();
        for (String key : objects.keySet()) {
            map.put(key, objects.get(key).deepCopy());
        }
        return new LispMapLiteral(map);
    }

    @Override
    public String lispStr() throws LispException {
        StringBuilder str = new StringBuilder("{");
        boolean first = true;
        for (String key : objects.keySet()) {
            if (!first) {
                str.append(" ");
            }
            str.append("'").append(key).append("' ").append(objects.get(key).lispStr());
            first = false;
        }
        str.append("}");
        return str.toString();
    }

    @Override
    public int lispLen() {
        return objects.size();
    }

    @Override
    public String toString() {
        return "map[" + objects.size() + "]";
    }

    @Override
    public String getType() {
        return "map";
    }
}
