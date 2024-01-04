package tripleo.elijah.javac_model.lang.model.element;

public interface Name extends CharSequence {
	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	boolean contentEquals(CharSequence cs);
}
