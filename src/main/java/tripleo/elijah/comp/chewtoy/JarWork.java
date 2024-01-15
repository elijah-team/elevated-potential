package tripleo.elijah.comp.chewtoy;

import clojure.lang.Var;
import tripleo.elijah.comp.internal.WorkException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

public interface JarWork {
	default void logProgress(String result) {
		SimplePrintLoggerToRemoveSoon.println_out_5(result);
	}

	void work() throws WorkException;

	void callfoo(Var foo);
}
