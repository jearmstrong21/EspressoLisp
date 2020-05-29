package p0nki.simpleclojure;

import java.util.function.Predicate;

//Taken from CommandParser framework, made by me, no big deal
public class CodeReader {

    public static Predicate<Character> isSpace = ch -> ch == ' ';
    public static Predicate<Character> isNotSpace = ch -> ch != ' ';

    private final String buffer;
    private int index;

    public CodeReader(String buffer) {
        this.buffer = buffer;
        index = 0;
    }

    public char peek() {
        return buffer.charAt(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void flushSpace() {
        while (peek() == ' ') next();
    }

    public String readWhile(Predicate<Character> predicate) {
        StringBuilder str = new StringBuilder();
        char ch;
        while (canRead() && predicate.test(ch = next())) {
            str.append(ch);
        }
        return str.toString();
    }

    public boolean canRead() {
        return index < buffer.length();
    }

    public char next() {
        if (!canRead()) throw new IndexOutOfBoundsException(index + "");
        return buffer.charAt(index++);
    }

}
