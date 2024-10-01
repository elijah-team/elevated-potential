/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;

import java.util.*;

public interface ErrSink {

	void reportError(int code, String message);

	enum Errors {
		ERROR, INFO, WARNING, EXCEPTION, DIAGNOSTIC
	}

	int errorCount();

	void exception(Exception exception);

	void info(String format);

	List<Pair<Errors, Object>> list();

	void reportDiagnostic(Diagnostic diagnostic);

	/* @ ensures errorCount() == \old errorCount + 1 */
	void reportError(String s);

	void reportWarning(String s);
}

//
//
//
