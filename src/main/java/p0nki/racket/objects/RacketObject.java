package p0nki.racket.objects;

public abstract class RacketObject {

    public abstract String debugString();

    public abstract String getType();

    public final RacketNumeric asNumeric() {
        if (!isNumeric()) throw new AssertionError();
        return (RacketNumeric) this;
    }

    public final boolean isNumeric(){
        return this instanceof RacketNumeric;
    }

}
