package p0nki.racket.run;

import org.junit.Test;
import p0nki.racket.exceptions.RacketException;
import p0nki.racket.objects.RacketObject;
import p0nki.racket.token.CLJTokenizer;

@SuppressWarnings("unused")
public class RacketContextTest {

    private static void log(RacketObject object) {
        System.out.println("---- OBJECT ----");
        System.out.println(object.getType());
        System.out.println(object.debugString());
    }

    @Test
    public void test() throws RacketException {
        RacketContext ctx = new RacketContext();
        System.out.println(ctx.evaluate(CLJTokenizer.tokenize("(+ 4 3)")));
//        Assert.assertEquals(4 + 3 * (2 - 5), ctx.read("(+ 4(* 3(- 2 5))").asNumeric().getDecimal().doubleValue(), 0);
//        Assert.assertEquals(4 + 3 * (2 - 5), ctx.read("(+ 4 (* 3 (- 2 5))").asNumeric().getDecimal().doubleValue(), 0);
//        Assert.assertEquals(4 + (-5), ctx.read("(+ 4 (- 5))").asNumeric().getDecimal().doubleValue(), 0);
//        Assert.assertEquals(4 - (-5), ctx.read("(+ 4 (- -5))").asNumeric().getDecimal().doubleValue(), 0);
//        Assert.assertEquals(4 / (-2), ctx.read("(/ 4 (- 2))").asNumeric().getDecimal().doubleValue(), 0);
//        Assert.assertEquals(4 * 3, ctx.read("(* 4 3)").asNumeric().getDecimal().doubleValue(), 0);
    }

}
