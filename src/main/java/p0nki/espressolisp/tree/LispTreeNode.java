package p0nki.espressolisp.tree;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.run.LispContext;

public interface LispTreeNode {

    LispObject evaluate(LispContext context) throws LispException;

    String debugStringify(String indent);

}
