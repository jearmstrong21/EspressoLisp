package p0nki.simpleclojure;

import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.functions.*;
import p0nki.simpleclojure.objects.CLJNumeric;
import p0nki.simpleclojure.objects.CLJObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CLJContext {

    private final Set<CLJFunction> functions;

    public CLJContext() {
        functions = new HashSet<>();
        functions.add(CLJAddition.INSTANCE);
        functions.add(CLJSubtraction.INSTANCE);
        functions.add(CLJMultiplication.INSTANCE);
        functions.add(CLJDivision.INSTANCE);
        functions.add(CLJNegate.INSTANCE);
    }

    public CLJObject read(String code) throws ClojureException {
        return read(new CodeReader(code));
    }

    public CLJObject read(CodeReader reader) throws ClojureException {
        if (!reader.canRead()) return null;
        if (reader.peek() == ')') return null;
        if (reader.peek() != '(') {
            String str = "";
            while (true) {
                char ch = reader.peek();
                if (ch != ' ' && ch != '(' && ch != ')') {
                    str += ch;
                    reader.next();
                } else {
                    break;
                }
            }
//            System.out.println(str);
            return new CLJNumeric(new BigDecimal(str));
        }
        reader.next(); // skip `(`
        String op = reader.readWhile(ch -> ch != ' ' && ch != ')'); // works since we have a guaranteed space
//        System.out.println("OP " + op);
        List<CLJFunction> applicable = functions.stream().filter(func -> func.getName().equals(op)).collect(Collectors.toList());
        List<CLJObject> objects = new ArrayList<>();
        while (true) {
            CLJObject object = read(reader);
            reader.flushSpace();
            if (object == null) break;
            objects.add(object);
        }
        applicable = applicable.stream().filter(func -> func.operatesUpon(objects)).collect(Collectors.toList());
        if (applicable.size() == 0) throw new ClojureException("No applicable operation");
        if (applicable.size() > 1)
            throw new ClojureException("Too many applicable operations"); // this should never happen by the way
        return applicable.get(0).operate(this, objects);
    }

}
