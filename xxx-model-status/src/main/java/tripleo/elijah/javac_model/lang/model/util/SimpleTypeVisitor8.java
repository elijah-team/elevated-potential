package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.IntersectionType;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_8;

@SupportedSourceVersion(RELEASE_8)
public class SimpleTypeVisitor8<R, P> extends SimpleTypeVisitor7<R, P> {
	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected SimpleTypeVisitor8() {
		super(null);
	}

	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected SimpleTypeVisitor8(R defaultValue) {
		super(defaultValue);
	}

	@Override
	public R visitIntersection(IntersectionType t, P p) {
		return defaultAction(t, p);
	}
}
