package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.ModuleElement;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_14;


@SupportedSourceVersion(RELEASE_14)
public abstract class AbstractElementVisitor9<R, P> extends AbstractElementVisitor8<R, P> {
    protected AbstractElementVisitor9(){
        super();
    }

    @Override
    public abstract R visitModule(ModuleElement e, P p);
}
