package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.diagnostic.*;

import java.util.*;

public interface ErrSink {
	int errorCount();

	void exception(Exception exception);

	void info(String format);

	List<Pair<Errors, Object>> list();

	void reportDiagnostic(Diagnostic diagnostic);

	/* @ ensures errorCount() == \old errorCount + 1 */
	void reportError(String s);
	void reportWarning(String s);

	enum Errors {
		ERROR, INFO, WARNING, EXCEPTION, DIAGNOSTIC
	}
}
