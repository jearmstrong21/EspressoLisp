package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

import java.util.ArrayList;
import java.util.List;

public class LispListLiteral extends LispLiteral {

    private final List<LispObject> objects;

    public LispListLiteral(List<LispObject> objects) {
        this.objects = objects;
    }

    @Override
    public LispObject deepCopy() throws LispException {
        List<LispObject> list = new ArrayList<>();
        for (LispObject object : objects) {
            LispObject deepCopy = object.deepCopy();
            list.add(deepCopy);
        }
        return new LispListLiteral(list);
    }

    public List<LispObject> getObjects() {
        return objects;
    }

    @Override
    public String lispStr() {
        StringBuilder builder = new StringBuilder("[");
        boolean first = true;
        for (LispObject object : objects) {
            if (!first) builder.append(" ");
            first = false;
            builder.append(object.lispStr());
        }
        return builder.toString() + "]";
    }

    @Override
    public int lispLen() {
        return objects.size();
    }

    @Override
    public String toString() {
        return "list" + lispStr();
    }

    @Override
    public String getType() {
        return "list";
    }

}
