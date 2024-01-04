package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.VariableElement;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_7;

@SupportedSourceVersion(RELEASE_7)
public class ElementKindVisitor7<R, P> extends ElementKindVisitor6<R, P> {
    @Deprecated(since="12")
    protected ElementKindVisitor7() {
        super(null); // Superclass constructor deprecated too
    }

    @Deprecated(since="12")
    protected ElementKindVisitor7(R defaultValue) {
        super(defaultValue); // Superclass constructor deprecated too
    }

    @Override
    public R visitVariableAsResourceVariable(VariableElement e, P p) {
        return defaultAction(e, p);
    }
}
