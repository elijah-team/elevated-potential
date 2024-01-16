package tripleo.elijah_elevated.clj;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;
import tripleo.elijah.clojure_support;

import java.io.IOException;

public class _ClojureEntryPoint {

	final static private Symbol CLOJURE_MAIN  = Symbol.intern("clojure.main");
	final static private Var    REQUIRE       = RT.var("clojure.core", "require");
	final static private Var    LEGACY_REPL   = RT.var("clojure.main", "legacy-repl");
	final static private Var    LEGACY_SCRIPT = RT.var("clojure.main", "legacy-script");
	final static private Var    MAIN          = RT.var("clojure.main", "main");

	public static void legacy_repl(String[] args) {
		RT.init();
		REQUIRE.invoke(CLOJURE_MAIN);
		LEGACY_REPL.invoke(RT.seq(args));
	}

	public static void legacy_script(String[] args) {
		RT.init();
		REQUIRE.invoke(CLOJURE_MAIN);
		LEGACY_SCRIPT.invoke(RT.seq(args));
	}

	public /*static*/ void main(String[] args) {
		RT.init();
		REQUIRE.invoke(CLOJURE_MAIN);

		final String cs  = "tripleo.elijah.clojure-support";
		final Symbol scs = Symbol.intern(cs);
		var i = REQUIRE.invoke(scs);
//		REQUIRE.invoke(Clojure.read(cs));

		try {
			RT.loadResourceScript(clojure_support.class, "el-nothing", true);
		} catch (IOException aE) {
			throw new RuntimeException(aE);
		}

		final Var nothing = RT.var(cs, "el-nothing");
		nothing.applyTo(RT.seq(args));

//		MAIN.applyTo(RT.seq(args));
		int y=2;
	}
}
