package p0nki.espressolisp.tree;

import org.json.JSONException;
import org.junit.Test;
import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.token.LispTokenizer;

@SuppressWarnings("unused")
public class LispASTCreatorTest {

    private void run(String code) throws LispException, JSONException {
        System.out.println(code);
        System.out.println(LispASTCreator.parse(LispTokenizer.tokenize(code)).toDebugJSON().toString(5));
        System.out.println();
    }

    @Test
    public void test() {
//        run("(concat 'hi' (concat ' ' 'world'))");
    }

}
