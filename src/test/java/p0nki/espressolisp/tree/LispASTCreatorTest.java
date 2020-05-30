package p0nki.espressolisp.tree;

import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.token.LispToken;
import p0nki.espressolisp.token.LispTokenizer;

import java.util.List;

@SuppressWarnings("unused")
public class LispASTCreatorTest {

    @Test
    public void test() throws LispException {
        String code = "(def (blah 3 4)5 (       no-args) looks-like-no-args-but-is-actually-variable)";
        List<LispToken> tokens = LispTokenizer.tokenize(code);
//        System.out.println(tokens.stream().map(RacketToken::toString).collect(Collectors.joining("\n")));
        LispTreeNode node = LispASTCreator.parse(tokens);
//        System.out.println();
//        System.out.println(code);
//        System.out.println();
//        System.out.println(node.debugStringify(""));
    }

}
