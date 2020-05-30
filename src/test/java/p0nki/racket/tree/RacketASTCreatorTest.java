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
        List<RacketToken> tokens = RacketTokenizer.tokenize("(def (blah 3 4) 5 (no-args 2))");
        System.out.println(tokens.stream().map(RacketToken::toString).collect(Collectors.joining("\n")));
        RacketTreeNode node = RacketASTCreator.parse(tokens);
        System.out.println(node.debugStringify(""));
    }

}
