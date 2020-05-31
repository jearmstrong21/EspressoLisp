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

    private StringBuffer buffer = new StringBuffer();

    private void flushBuffer() {
        buffer = new StringBuffer(buffer.toString().trim());
        if (buffer.length() == 0) return;
        tokens.add(new LispLiteralToken(buffer.toString(), reader.getIndex() - buffer.length() - 1, reader.getIndex() - 1));
        buffer = new StringBuffer();
    }

    private void parse() {
        boolean inQuote = false;
        boolean isEscaped = false;
        while (reader.canRead()) {
            char ch = reader.next();
            if (inQuote) {
                if (ch == '\\') {
                    isEscaped = true;
                }
                if (ch == '\'' && !isEscaped) {
                    flushBuffer();
                    tokens.add(new LispToken(LispTokenType.QUOTE, reader.getIndex() - 1, reader.getIndex()));
                    inQuote = false;
                } else {
                    buffer.append(ch);
                }
                if (ch != '\\' && isEscaped) isEscaped = false;
            } else {
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
                if (ch == '\'') {
                    flushBuffer();
                    tokens.add(new LispToken(LispTokenType.QUOTE, reader.getIndex() - 1, reader.getIndex()));
                    inQuote = true;
                    continue;
                }
                buffer.append(ch);
            }
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
