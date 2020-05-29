package p0nki.simpleclosure;

import org.junit.Test;
import p0nki.simpleclojure.CLJContext;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.objects.CLJObject;

@SuppressWarnings("unused")
public class CLJContextTest {

    private static void log(CLJObject object){
        System.out.println("---- OBJECT ----");
        System.out.println(object.getType());
        System.out.println(object.debugString());
    }

    @Test
    public void test() throws ClojureException {
        CLJContext ctx = new CLJContext();
        log(ctx.read("(+ 4(* 3(- 2 5))"));
        log(ctx.read("(+ 4 (* 3 (- 2 5))"));
        log(ctx.read("(+ 4 (- 5))"));
        log(ctx.read("(+ 4 (- -5))"));
    }

}
