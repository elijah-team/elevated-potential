package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.*;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_6;

@SupportedSourceVersion(RELEASE_6)
public abstract class AbstractAnnotationValueVisitor6<R, P>
		implements AnnotationValueVisitor<R, P> {

	@Deprecated(since = "9")
	protected AbstractAnnotationValueVisitor6() {
	}

	@Override
	public final R visit(AnnotationValue av, P p) {
		return av.accept(this, p);
	}

	@Override
	public final R visit(AnnotationValue av) {
		return av.accept(this, null);
	}

	@Override
	public R visitUnknown(AnnotationValue av, P p) {
		throw new UnknownAnnotationValueException(av, p);
	}
}
