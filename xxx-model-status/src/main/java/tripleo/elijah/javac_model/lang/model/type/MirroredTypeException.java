package tripleo.elijah.javac_model.lang.model.type;

import java.io.IOException;
import java.io.ObjectInputStream;


public class MirroredTypeException extends MirroredTypesException {

    //private static final long serialVersionUID = 269;

    private transient TypeMirror type;          // cannot be serialized

    public MirroredTypeException(TypeMirror type) {
        super("Attempt to access Class object for TypeMirror " + type.toString(), type);
        this.type = type;
    }

    public TypeMirror getTypeMirror() {
        return type;
    }

    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        type = null;
        types = null;
    }
}
