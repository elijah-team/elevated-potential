package tripleo.elijah.javac_model.lang.model.type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;


public class MirroredTypesException extends RuntimeException {

    //private static final long serialVersionUID = 269;

    transient List<? extends TypeMirror> types; // cannot be serialized

    /*
     * Trusted constructor to be called by MirroredTypeException.
     */
    MirroredTypesException(String message, TypeMirror type) {
        super(message);
        List<TypeMirror> tmp = (new ArrayList<>());
        tmp.add(type);
        types = Collections.unmodifiableList(tmp);
    }

    public MirroredTypesException(List<? extends TypeMirror> types) {
        super("Attempt to access Class objects for TypeMirrors " +
					  (types = // defensive copy
					   new ArrayList<>(types)));
        this.types = Collections.unmodifiableList(types);
    }

    public List<? extends TypeMirror> getTypeMirrors() {
        return types;
    }

    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        types = null;
    }
}
