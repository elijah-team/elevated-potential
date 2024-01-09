package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.*;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_6;

@SupportedSourceVersion(RELEASE_6)
public abstract class AbstractTypeVisitor6<R, P> implements TypeVisitor<R, P> {
	@Deprecated(since = "9")
	protected AbstractTypeVisitor6() {
	}

	@Override
	public final R visit(TypeMirror t) {
		return t.accept(this, null);
	}

	@Override
	public final R visit(TypeMirror t, P p) {
		return t.accept(this, p);
	}

	@Override
	public R visitUnknown(TypeMirror t, P p) {
		throw new UnknownTypeException(t, p);
	}

	@Override
	public R visitUnion(UnionType t, P p) {
		return visitUnknown(t, p);
	}

	@Override
	public R visitIntersection(IntersectionType t, P p) {
		return visitUnknown(t, p);
	}
}
