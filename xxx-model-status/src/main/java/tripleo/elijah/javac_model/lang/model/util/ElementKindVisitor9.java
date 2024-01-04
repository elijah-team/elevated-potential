package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.ModuleElement;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_14;

@SupportedSourceVersion(RELEASE_14)
public class ElementKindVisitor9<R, P> extends ElementKindVisitor8<R, P> {
    protected ElementKindVisitor9() {
        super(null);
    }

    protected ElementKindVisitor9(R defaultValue) {
        super(defaultValue);
    }

    @Override
    public R visitModule(ModuleElement e, P p) {
        return defaultAction(e, p);
    }
}
