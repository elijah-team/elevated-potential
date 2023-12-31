package tripleo.elijah.javac_model.lang.model.util;


import tripleo.elijah.javac_model.lang.model.element.*;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_6;


@SupportedSourceVersion(RELEASE_6)
public class SimpleElementVisitor6<R, P> extends AbstractElementVisitor6<R, P> {
	protected final R DEFAULT_VALUE;

	@Deprecated(since = "9")
	protected SimpleElementVisitor6() {
		DEFAULT_VALUE = null;
	}

	@Deprecated(since = "9")
	protected SimpleElementVisitor6(R defaultValue) {
		DEFAULT_VALUE = defaultValue;
	}

	@Override
	public R visitPackage(PackageElement e, P p) {
		return defaultAction(e, p);
	}

	protected R defaultAction(Element e, P p) {
		return DEFAULT_VALUE;
	}

	@Override
	public R visitType(TypeElement e, P p) {
		return defaultAction(e, p);
	}

	@Override
	public R visitVariable(VariableElement e, P p) {
		if (e.getKind() != ElementKind.RESOURCE_VARIABLE)
			return defaultAction(e, p);
		else
			return visitUnknown(e, p);
	}

	@Override
	public R visitExecutable(ExecutableElement e, P p) {
		return defaultAction(e, p);
	}

	@Override
	public R visitTypeParameter(TypeParameterElement e, P p) {
		return defaultAction(e, p);
	}
}
