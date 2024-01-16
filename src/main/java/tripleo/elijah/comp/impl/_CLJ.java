package tripleo.elijah.comp.impl;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import tripleo.elijah.clojure_support__init;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah_elevated.clj._ClojureEntryPoint;

class _CLJ {
	_ClojureEntryPoint m =new _ClojureEntryPoint();
	Object serverInstance;

	boolean clj(final Compilation0 c) {
		final ErrSink logger = c.getErrSink();
		final int     port   = 7778;

		if (null != serverInstance) {
			logger.warn("nrepl server already running, refusing to start another.");
			return true;
		}
		try {
			m.main(new String[0]);

			clojure_support__init.__init0();
			clojure_support__init.load();

			IFn require = Clojure.var("clojure.core", "require");
//				require.invoke(Clojure.read("clojure.tools.nrepl.server"));
			final String cs = "tripleo.elijah.clojure-support";
			require.invoke(Clojure.read(cs));

			require.invoke(
					Clojure.var("clojure.core", "symbol")
			                  .invoke(cs));

			IFn nothing = Clojure.var(cs, "el-nothing");
			final Object x = nothing.invoke();

//				IFn chan = Clojure.var(cs, "el-make-chan");
//				IFn server = Clojure.var(cs, clojure_support__init.const__12.sym.getName());
//				serverInstance = server.invoke(Clojure.read(":port"), port);
//				logger.info("Started clojure nREPL on port " + port);

			logger.info("nothing = " + x);

			Clojure.var("clojure.core", "shutdown-agents").invoke();
		} catch (Throwable e) {
			logger.error("Error starting nrepl", e);
		}
		return false;
	}
}
