package p0nki.simpleclosure;

import org.junit.Assert;
import org.junit.Test;
import p0nki.simpleclojure.CLJContext;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.objects.CLJObject;

@SuppressWarnings("unused")
public class CLJContextTest {

    private static void log(CLJObject object) {
        System.out.println("---- OBJECT ----");
        System.out.println(object.getType());
        System.out.println(object.debugString());
    }

    @Test
    public void test() throws ClojureException {
        CLJContext ctx = new CLJContext();
        Assert.assertEquals(4 + 3 * (2 - 5), ctx.read("(+ 4(* 3(- 2 5))").asNumeric().getDecimal().doubleValue(), 0);
        Assert.assertEquals(4 + 3 * (2 - 5), ctx.read("(+ 4 (* 3 (- 2 5))").asNumeric().getDecimal().doubleValue(), 0);
        Assert.assertEquals(4 + (-5), ctx.read("(+ 4 (- 5))").asNumeric().getDecimal().doubleValue(), 0);
        Assert.assertEquals(4 - (-5), ctx.read("(+ 4 (- -5))").asNumeric().getDecimal().doubleValue(), 0);
        Assert.assertEquals(4 / (-2), ctx.read("(/ 4 (- 2))").asNumeric().getDecimal().doubleValue(), 0);
        Assert.assertEquals(4 * 3, ctx.read("(* 4 3)").asNumeric().getDecimal().doubleValue(), 0);
    }

}
