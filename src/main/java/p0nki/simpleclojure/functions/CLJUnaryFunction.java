package p0nki.simpleclojure.functions;

import p0nki.simpleclojure.run.CLJContext;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.objects.CLJObject;

import java.util.List;

abstract class CLJUnaryFunction extends CLJFunction {
    @Override
    public boolean operatesUpon(List<CLJObject> objects) {
        if (objects.size() != 1) return false;
        return _operatesUpon(objects.get(0));
    }

    protected abstract boolean _operatesUpon(CLJObject a);

    protected abstract CLJObject _operate(CLJContext context, CLJObject a);

    @Override
    public CLJObject operate(CLJContext context, List<CLJObject> objects) throws ClojureException {
        return _operate(context, objects.get(0));
    }
}
