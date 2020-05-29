package p0nki.simpleclojure.objects;

import p0nki.simpleclojure.objects.CLJNumeric;

public abstract class CLJObject {

    public abstract String debugString();

    public abstract String getType();

    public final CLJNumeric asNumeric() {
        if (!isNumeric()) throw new AssertionError();
        return (CLJNumeric) this;
    }

    public final boolean isNumeric(){
        return this instanceof CLJNumeric;
    }

}
