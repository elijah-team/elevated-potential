package tripleo.elijah.javac_model.tools;

import java.util.Locale;

public interface Diagnostic<S> {

	long NOPOS = -1;

	Kind getKind();

	S getSource();

	long getPosition();

	long getStartPosition();

	long getEndPosition();

	long getLineNumber();

	long getColumnNumber();

	String getCode();

	String getMessage(Locale locale);

	enum Kind {
		ERROR,
		WARNING,
		MANDATORY_WARNING,
		NOTE,
		OTHER,
	}
}
