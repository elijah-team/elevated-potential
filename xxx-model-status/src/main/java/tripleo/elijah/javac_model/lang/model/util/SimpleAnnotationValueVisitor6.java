package tripleo.elijah.javac_model.lang.model.util;


import tripleo.elijah.javac_model.lang.model.element.*;
import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.List;

import static javax.lang.model.SourceVersion.RELEASE_6;

@SupportedSourceVersion(RELEASE_6)
public class SimpleAnnotationValueVisitor6<R, P>
		extends AbstractAnnotationValueVisitor6<R, P> {

	protected final R DEFAULT_VALUE;

	@Deprecated(since = "9")
	protected SimpleAnnotationValueVisitor6() {
		super();
		DEFAULT_VALUE = null;
	}

	@Deprecated(since = "9")
	protected SimpleAnnotationValueVisitor6(R defaultValue) {
		super();
		DEFAULT_VALUE = defaultValue;
	}

	@Override
	public R visitBoolean(boolean b, P p) {
		return defaultAction(b, p);
	}

	protected R defaultAction(Object o, P p) {
		return DEFAULT_VALUE;
	}

	@Override
	public R visitByte(byte b, P p) {
		return defaultAction(b, p);
	}

	@Override
	public R visitChar(char c, P p) {
		return defaultAction(c, p);
	}

	@Override
	public R visitDouble(double d, P p) {
		return defaultAction(d, p);
	}

	@Override
	public R visitFloat(float f, P p) {
		return defaultAction(f, p);
	}

	@Override
	public R visitInt(int i, P p) {
		return defaultAction(i, p);
	}

	@Override
	public R visitLong(long i, P p) {
		return defaultAction(i, p);
	}

	@Override
	public R visitShort(short s, P p) {
		return defaultAction(s, p);
	}

	@Override
	public R visitString(String s, P p) {
		return defaultAction(s, p);
	}

	@Override
	public R visitType(TypeMirror t, P p) {
		return defaultAction(t, p);
	}

	@Override
	public R visitEnumConstant(VariableElement c, P p) {
		return defaultAction(c, p);
	}

	@Override
	public R visitAnnotation(AnnotationMirror a, P p) {
		return defaultAction(a, p);
	}

	@Override
	public R visitArray(List<? extends AnnotationValue> vals, P p) {
		return defaultAction(vals, p);
	}
}
