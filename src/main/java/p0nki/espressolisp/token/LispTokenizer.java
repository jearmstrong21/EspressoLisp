package p0nki.espressolisp.token;

import p0nki.espressolisp.LispCodeReader;

import java.util.ArrayList;
import java.util.List;

public class LispTokenizer {

    private final List<LispToken> tokens;
    private final LispCodeReader reader;

    private LispTokenizer(List<LispToken> tokens, LispCodeReader reader) {
        this.tokens = tokens;
        this.reader = reader;
        parse();
        flushBuffer();
    }

    private String buffer = "";

    private void flushBuffer() {
        buffer = buffer.trim();
        if (buffer.length() == 0) return;
        tokens.add(new LispUnquotedLiteralToken(buffer, reader.getIndex() - buffer.length(), reader.getIndex()));
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
                tokens.add(new LispToken(LispTokenType.LEFT_PAREN, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == ')') {
                flushBuffer();
                tokens.add(new LispToken(LispTokenType.RIGHT_PAREN, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == '[') {
                flushBuffer();
                tokens.add(new LispToken(LispTokenType.LEFT_BRACKET, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == ']') {
                flushBuffer();
                tokens.add(new LispToken(LispTokenType.RIGHT_BRACKET, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            if (ch == ',') {
                flushBuffer();
                tokens.add(new LispToken(LispTokenType.COMMA, reader.getIndex() - 1, reader.getIndex()));
                continue;
            }
            buffer += ch;
        }
    }

    public static List<LispToken> tokenize(String code) {
        return tokenize(new LispCodeReader(code));
    }

    public static List<LispToken> tokenize(LispCodeReader reader) {
        List<LispToken> tokens = new ArrayList<>();
        new LispTokenizer(tokens, reader);
        return tokens;
    }

}
