package p0nki.simpleclojure.functions;

import p0nki.simpleclojure.CLJContext;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.objects.CLJObject;

import java.util.List;

abstract class CLJBinaryFunction extends CLJFunction {
    @Override
    public final boolean operatesUpon(List<CLJObject> objects) {
        if (objects.size() != 2) return false;
        return _operatesUpon(objects.get(0), objects.get(1));
    }

    protected abstract boolean _operatesUpon(CLJObject a, CLJObject b);

    protected abstract CLJObject _operate(CLJContext context, CLJObject a, CLJObject b) throws ClojureException;

    @Override
    public CLJObject operate(CLJContext context, List<CLJObject> objects) throws ClojureException {
        return _operate(context, objects.get(0), objects.get(1));
    }

}
