package tripleo.elijah.javac_model.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_8;

@SupportedSourceVersion(RELEASE_8)
public class SimpleAnnotationValueVisitor8<R, P> extends SimpleAnnotationValueVisitor7<R, P> {
	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected SimpleAnnotationValueVisitor8() {
		super(null);
	}

	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected SimpleAnnotationValueVisitor8(R defaultValue) {
		super(defaultValue);
	}
}
