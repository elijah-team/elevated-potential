package tripleo.elijah.comp.internal;

import clojure.lang.Var;

public interface JarWork {
	default void logProgress(String result) {
		System.out.println(result);
	}

	void work() throws WorkException;

	void callfoo(Var foo);
}
