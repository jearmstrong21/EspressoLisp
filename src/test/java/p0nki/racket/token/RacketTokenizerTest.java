package p0nki.racket.token;

import org.junit.Test;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class RacketTokenizerTest {

    @Test
    public void test() {
        String code = "(defn *3[x, y](+ y (* 5 x))) (*3 4 6) (* 3 4)";
        System.out.println(code);
        System.out.println(CLJTokenizer.tokenize(code).stream().map(RacketToken::toString).collect(Collectors.joining("\n")));
    }

}
