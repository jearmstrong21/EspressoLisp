package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
    public String lispStr() throws LispException {
        StringJoiner joiner = new StringJoiner(", ");
        for (LispObject object : objects) {
            String lispStr = object.lispStr();
            joiner.add(lispStr);
        }
        return "[" + joiner.toString() + "]";
    }

    @Override
    public int lispLen() {
        return objects.size();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        for (LispObject object : objects) {
            try {
                joiner.add(object.lispStr());
            } catch (LispException e) {
                throw new RuntimeException(e);
            }
        }
        return "list[" + joiner.toString() + "]";
    }

    @Override
    public String getType() {
        return "list";
    }

}
