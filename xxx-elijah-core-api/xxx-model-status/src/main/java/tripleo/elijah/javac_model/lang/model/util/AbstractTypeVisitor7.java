package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.UnionType;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_7;

@SupportedSourceVersion(RELEASE_7)
public abstract class AbstractTypeVisitor7<R, P> extends AbstractTypeVisitor6<R, P> {
	@Deprecated(since = "12")
	protected AbstractTypeVisitor7() {
		super();  // Superclass constructor deprecated too
	}

	@Override
	public abstract R visitUnion(UnionType t, P p);
}
