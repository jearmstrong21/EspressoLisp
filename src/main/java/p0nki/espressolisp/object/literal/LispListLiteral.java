package p0nki.espressolisp.object.literal;

import p0nki.espressolisp.object.LispObject;

import java.util.List;
import java.util.stream.Collectors;

public class LispListLiteral extends LispLiteral {

    private final List<LispObject> objects;

    public LispListLiteral(List<LispObject> objects) {
        this.objects = objects;
    }

    @Override
    public String lispStr() {
        return "[" + objects.stream().map(LispObject::lispStr).collect(Collectors.joining(", ")) + "]";
    }

    @Override
    public String toString() {
        return "[" + objects.stream().map(LispObject::lispStr).collect(Collectors.joining(", ")) + "]";
    }

    @Override
    public String getType() {
        return "list";
    }

}
