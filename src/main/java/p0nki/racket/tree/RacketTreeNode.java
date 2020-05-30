package p0nki.racket.tree;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.object.RacketObject;
import p0nki.racket.run.RacketContext;

public interface RacketTreeNode {

    RacketObject evaluate(RacketContext context) throws RacketException;

    String debugStringify(String indent);

}
