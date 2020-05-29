package p0nki.racket.run;

import p0nki.racket.exceptions.RacketException;
import p0nki.racket.functions.*;
import p0nki.racket.objects.RacketObject;
import p0nki.racket.token.RacketToken;
import p0nki.racket.token.CLJTokenType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RacketContext {

    private final Set<RacketFunction> functions;

    public RacketContext() {
        functions = new HashSet<>();
        functions.add(RacketAddition.INSTANCE);
        functions.add(RacketSubtraction.INSTANCE);
        functions.add(RacketMultiplication.INSTANCE);
        functions.add(RacketDivision.INSTANCE);
        functions.add(RacketNegate.INSTANCE);
    }

    public RacketObject evaluate(List<RacketToken> tokens) throws RacketException {
        if (tokens.size() == 0) throw new IllegalArgumentException("Cannot parse an empty program");
        RacketToken leftParen = tokens.remove(0);
        if (leftParen.getType() != CLJTokenType.LEFT_PAREN)
            throw RacketException.expected(CLJTokenType.LEFT_PAREN, leftParen);
        if(tokens.size() == 0) throw new RacketException("Expected token after `(`", leftParen);
        RacketToken expressionStart = tokens.remove(0);
        if(expressionStart.getType() != CLJTokenType.UNQUOTED_LITERAL) throw new RacketException("Expected UNQUOTED_LITERAL", expressionStart);
    }

}
