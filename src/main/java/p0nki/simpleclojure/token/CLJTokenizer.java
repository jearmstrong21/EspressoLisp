package p0nki.simpleclojure.token;

import p0nki.simpleclojure.CodeReader;

import java.util.ArrayList;
import java.util.List;

public class CLJTokenizer {

    private final List<CLJToken> tokens;
    private final CodeReader reader;

    private CLJTokenizer(List<CLJToken> tokens, CodeReader reader) {
        this.tokens = tokens;
        this.reader = reader;
        parse();
    }

    private String buffer = "";

    private void flushBuffer() {
        buffer = buffer.trim();
        if (buffer.length() == 0) return;
        tokens.add(new CLJUnquotedLiteralToken(buffer));
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
                tokens.add(new CLJToken(CLJTokenType.LEFT_PAREN));
                continue;
            }
            if (ch == ')') {
                flushBuffer();
                tokens.add(new CLJToken(CLJTokenType.RIGHT_PAREN));
                continue;
            }
            if (ch == '[') {
                flushBuffer();
                tokens.add(new CLJToken(CLJTokenType.LEFT_BRACKET));
                continue;
            }
            if (ch == ']') {
                flushBuffer();
                tokens.add(new CLJToken(CLJTokenType.RIGHT_BRACKET));
                continue;
            }
            if (ch == ',') {
                flushBuffer();
                tokens.add(new CLJToken(CLJTokenType.COMMA));
                continue;
            }
            buffer += ch;
        }
    }

    public static List<CLJToken> tokenize(String code) {
        return tokenize(new CodeReader(code));
    }

    public static List<CLJToken> tokenize(CodeReader reader) {
        List<CLJToken> tokens = new ArrayList<>();
        new CLJTokenizer(tokens, reader);
        return tokens;
    }

}
