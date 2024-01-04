package tripleo.elijah.javac_model.lang.model.element;

public interface Name extends CharSequence {
    @Override
	boolean equals(Object obj);

    @Override
	int hashCode();

    boolean contentEquals(CharSequence cs);
}
