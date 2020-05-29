package p0nki.simpleclojure.run;

import p0nki.simpleclojure.CodeReader;
import p0nki.simpleclojure.exceptions.ClojureException;
import p0nki.simpleclojure.functions.*;
import p0nki.simpleclojure.objects.CLJNumeric;
import p0nki.simpleclojure.objects.CLJObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CLJContext {

    private final Set<CLJFunction> functions;

    public CLJContext() {
        functions = new HashSet<>();
        functions.add(CLJAddition.INSTANCE);
        functions.add(CLJSubtraction.INSTANCE);
        functions.add(CLJMultiplication.INSTANCE);
        functions.add(CLJDivision.INSTANCE);
        functions.add(CLJNegate.INSTANCE);
    }



}
