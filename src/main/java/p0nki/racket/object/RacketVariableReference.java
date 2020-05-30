package p0nki.racket.object;

public class RacketVariableReference extends RacketObject {

    private final String name;

    public RacketVariableReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ref[" + name + "]";
    }

    @Override
    public String getType() {
        return "ref";
    }
}
