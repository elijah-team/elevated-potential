package tripleo.elijah_elevated_durable.comp;

import clojure.lang.*;
import tripleo.elijah_elevateder.comp.internal.WorkException;

import java.io.File;
import java.io.IOException;
import java.net.*;

public class JarWorkImpl implements JarWork {
	private final String jarname = "../../lib2/myapp-0.0.1-standalone.jar";
	private final File   myJar   = new File(jarname);
	private final URI    uri     = myJar.toURI();
	private final URL    url;

	public JarWorkImpl() throws WorkException {
		try {
			url = uri.toURL();
		} catch (MalformedURLException aE) {
			throw new WorkException(aE);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void work() throws WorkException {
		try (URLClassLoader child2 = new URLClassLoader(
				new URL[]{url},
				this.getClass().getClassLoader()
		)) {
			Class classToLoad = Class.forName("tripleo.elijah.clojure_support__init", true, child2);
			//Method method      = classToLoad.getDeclaredMethod("myMethod");
			//Object instance    = classToLoad.newInstance();
			//Object result = method.invoke(instance);
		} catch (IOException aE) {
			throw new WorkException(aE);
		} catch (ClassNotFoundException aE) {
			//noinspection unused
			int shutup=1;
			throw new WorkException(aE);
		}

		try {
			RT.load("tripleo.elijah.clojure_support");

			// Get a reference to the foo function.
			Var foo = RT.var("tripleo.elijah.clojure-support", "el-nothing");

			callfoo(foo);

			//@SuppressWarnings("unused") tripleo.elijah.clojure_support__init i;
			//var                                                              x = el_make_chan.invokeStatic();

			int y = 2;
		} catch (IOException | ClassNotFoundException aE) {
			throw new WorkException(aE);
		}

	}

	@Override
	public void callfoo(final Var foo) {
		// Call it!
		Object result = foo.invoke(/*"Hi", "there"*/);

		logProgress("result is a " + result.getClass());
		logProgress("result value is " + result);

		if (result instanceof LazySeq seq) {
			for (Object o : seq) {
				logProgress("" + o);
			}
		}
	}

}
