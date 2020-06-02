package p0nki.espressolisp;

//Taken from CommandParser framework, made by me, no big deal
public class LispCodeReader {

    private final String buffer;
    private int index;

    public LispCodeReader(String buffer) {
        this.buffer = buffer;
        index = 0;
    }

    public int getIndex() {
        return index;
    }

    public boolean canRead() {
        return index < buffer.length();
    }

    public char next() {
        if (!canRead()) throw new IndexOutOfBoundsException(index + "");
        return buffer.charAt(index++);
    }

}
