package tripleo.elijah.javac_model.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_7;

@SupportedSourceVersion(RELEASE_7)
public abstract class AbstractAnnotationValueVisitor7<R, P> extends AbstractAnnotationValueVisitor6<R, P> {

    @Deprecated(since="12")
    protected AbstractAnnotationValueVisitor7() {
        super(); // Superclass constructor deprecated too
    }
}
