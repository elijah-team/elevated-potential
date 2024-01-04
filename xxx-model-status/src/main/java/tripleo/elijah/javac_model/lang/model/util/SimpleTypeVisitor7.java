package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.UnionType;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_7;

@SupportedSourceVersion(RELEASE_7)
public class SimpleTypeVisitor7<R, P> extends SimpleTypeVisitor6<R, P> {
    @Deprecated(since="12")
    protected SimpleTypeVisitor7(){
        super(null); // Superclass constructor deprecated too
    }

    @Deprecated(since="12")
    protected SimpleTypeVisitor7(R defaultValue){
        super(defaultValue); // Superclass constructor deprecated too
    }

    @Override
    public R visitUnion(UnionType t, P p) {
        return defaultAction(t, p);
    }
}
