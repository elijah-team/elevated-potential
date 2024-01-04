package tripleo.elijah.javac_model.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_8;

@SupportedSourceVersion(RELEASE_8)
public class ElementKindVisitor8<R, P> extends ElementKindVisitor7<R, P> {
	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected ElementKindVisitor8() {
		super(null);
	}

	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected ElementKindVisitor8(R defaultValue) {
		super(defaultValue);
	}
}
