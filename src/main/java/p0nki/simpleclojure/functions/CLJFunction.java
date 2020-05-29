package p0nki.simpleclojure.functions;

import p0nki.simpleclojure.CLJContext;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.objects.CLJObject;

import java.util.List;

public abstract class CLJFunction {

    public abstract boolean operatesUpon(List<CLJObject> objects);

    public abstract CLJObject operate(CLJContext context, List<CLJObject> objects) throws ClojureException;

    public abstract String getName();

}
