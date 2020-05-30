package p0nki.racket.tree;

import org.junit.Test;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.token.RacketToken;
import p0nki.racket.token.RacketTokenizer;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class RacketASTCreatorTest {

    @Test
    public void test() throws RacketException {
        String code = "(def (blah 3 4)5 (       no-args) looks-like-no-args-but-is-actually-variable)";
        List<RacketToken> tokens = RacketTokenizer.tokenize(code);
//        System.out.println(tokens.stream().map(RacketToken::toString).collect(Collectors.joining("\n")));
        RacketTreeNode node = RacketASTCreator.parse(tokens);
//        System.out.println();
//        System.out.println(code);
//        System.out.println();
//        System.out.println(node.debugStringify(""));
    }

}
