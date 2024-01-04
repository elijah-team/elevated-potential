package tripleo.elijah.javac_model.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_8;


@SupportedSourceVersion(RELEASE_8)
public abstract class AbstractElementVisitor8<R, P> extends AbstractElementVisitor7<R, P> {
    @SuppressWarnings("deprecation") // Superclass constructor deprecated
    protected AbstractElementVisitor8(){
        super();
    }
}
