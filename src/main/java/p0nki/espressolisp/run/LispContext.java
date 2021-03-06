package p0nki.espressolisp.run;

import p0nki.espressolisp.exceptions.LispException;
import p0nki.espressolisp.library.LispBuiltinLibrary;
import p0nki.espressolisp.library.LispLibrary;
import p0nki.espressolisp.object.LispObject;
import p0nki.espressolisp.object.literal.LispNullLiteral;
import p0nki.espressolisp.object.reference.LispReference;
import p0nki.espressolisp.object.reference.LispStandardReferenceImpl;
import p0nki.espressolisp.token.LispTokenizer;
import p0nki.espressolisp.tree.LispASTCreator;
import p0nki.espressolisp.utils.LispLogger;

import java.util.*;

public class LispContext {

    private final LispContext parent;
    private final Map<String, LispLibrary> potentialLibraries;
    private final List<String> loadedLibraries;

    private final Map<String, LispReference> objects;
    private final LispLogger logger;

    public LispContext(LispLogger logger, LispContext parent) throws LispException {
        this.logger = logger;
        this.parent = parent;
        objects = new HashMap<>();
        LispBuiltinLibrary.INSTANCE.load(this);

        potentialLibraries = new HashMap<>();
        potentialLibraries.put("builtin", LispBuiltinLibrary.INSTANCE);

        loadedLibraries = new ArrayList<>();
        loadedLibraries.add("builtin");
    }

    public Set<String> keys() {
        return new HashSet<>(objects.keySet());
    }

    public void potentialLibrary(LispLibrary library) {
        potentialLibraries.put(library.getName(), library);
    }

    public void importLibrary(String name) throws LispException {
        if (!potentialLibraries.containsKey(name)) throw LispException.noLibraryWithName(name, null);
        if (loadedLibraries.contains(name)) logger.warn("Re-importing library " + name);
        potentialLibraries.get(name).load(this);
    }

    public LispLogger getLogger() {
        return logger;
    }

    public void overwrite(String name, LispReference ref) {
        objects.put(name, ref);
    }

    public LispReference set(String name, LispObject obj) throws LispException {
        get(name).set(obj);
        return get(name);
    }

    public Optional<LispContext> getParent() {
        return Optional.ofNullable(parent);
    }

    public LispContext push() throws LispException {
        LispContext ctx = new LispContext(logger, this);
        for (String key : objects.keySet()) ctx.objects.put(key, objects.get(key));
        return ctx;
    }

    public LispReference get(String name) {
        if (!objects.containsKey(name)) {
            objects.put(name, new LispReference(name, false, new LispStandardReferenceImpl(LispNullLiteral.INSTANCE)));
        }
        return objects.get(name);
    }

    public LispObject evaluate(String code) throws LispException {
        return LispASTCreator.parse(LispTokenizer.tokenize(code)).evaluate(this);
    }

}
