package tripleo.elijah_elevated_durable.comp;

import clojure.lang.Var;
import tripleo.elijah_elevateder.comp.i.WorkException;

public interface JarWork {
	default void logProgress(String result) {
		System.out.println(result);
	}

	void work() throws WorkException;

	void callfoo(Var foo);
}
