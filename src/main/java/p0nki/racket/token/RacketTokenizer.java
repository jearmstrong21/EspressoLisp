package p0nki.racket.token;

import p0nki.racket.RacketCodeReader;

import java.util.ArrayList;
import java.util.List;

public class RacketTokenizer {

    private final List<RacketToken> tokens;
    private final RacketCodeReader reader;

    private RacketTokenizer(List<RacketToken> tokens, RacketCodeReader reader) {
        this.tokens = tokens;
        this.reader = reader;
        parse();
    }

    private String buffer = "";

    private void flushBuffer() {
        buffer = buffer.trim();
        if (buffer.length() == 0) return;
        tokens.add(new RacketUnquotedLiteralToken(buffer, reader.getIndex() - buffer.length(), reader.getIndex()));
        buffer = "";
    }

    private void parse() {
        while (reader.canRead()) {
            char ch = reader.next();
            if (ch == ' ') {
                flushBuffer();
                continue;
            }
            if (ch == '(') {
                flushBuffer();
                tokens.add(new RacketToken(RacketTokenType.LEFT_PAREN, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == ')') {
                flushBuffer();
                tokens.add(new RacketToken(RacketTokenType.RIGHT_PAREN, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == '[') {
                flushBuffer();
                tokens.add(new RacketToken(RacketTokenType.LEFT_BRACKET, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == ']') {
                flushBuffer();
                tokens.add(new RacketToken(RacketTokenType.RIGHT_BRACKET, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == ',') {
                flushBuffer();
                tokens.add(new RacketToken(RacketTokenType.COMMA, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            buffer += ch;
        }
    }

    public static List<RacketToken> tokenize(String code) {
        return tokenize(new RacketCodeReader(code));
    }

    public static List<RacketToken> tokenize(RacketCodeReader reader) {
        List<RacketToken> tokens = new ArrayList<>();
        new RacketTokenizer(tokens, reader);
        return tokens;
    }

}