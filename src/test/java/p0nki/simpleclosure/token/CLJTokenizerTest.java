package p0nki.simpleclosure.token;

import org.junit.Test;
import p0nki.simpleclojure.token.CLJToken;
import p0nki.simpleclojure.token.CLJTokenizer;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CLJTokenizerTest {

    @Test
    public void test() {
        String code = "(defn *3[x, y](+ y (* 5 x))) (*3 4 6) (* 3 4)";
        System.out.println(code);
        System.out.println(CLJTokenizer.tokenize(code).stream().map(CLJToken::toString).collect(Collectors.joining("\n")));
    }

}
