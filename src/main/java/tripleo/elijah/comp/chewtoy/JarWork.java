package tripleo.elijah.comp.chewtoy;

import clojure.lang.Var;
import tripleo.elijah.comp.internal.WorkException;

public interface JarWork {
	default void logProgress(String result) {
		System.out.println(result);
	}

	void work() throws WorkException;

	void callfoo(Var foo);
}
