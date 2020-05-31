package p0nki.espressolisp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static <T> List<T> of(T... values) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, values);
        return list;
    }

}
