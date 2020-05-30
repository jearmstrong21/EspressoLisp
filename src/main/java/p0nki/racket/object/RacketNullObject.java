package p0nki.racket.object;

public class RacketNullObject extends RacketObject {

    public static final RacketNullObject INSTANCE = new RacketNullObject();

    private RacketNullObject() {

    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String getType() {
        return "null";
    }
}
