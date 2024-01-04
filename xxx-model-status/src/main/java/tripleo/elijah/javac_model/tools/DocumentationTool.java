package tripleo.elijah.javac_model.tools;

import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.Callable;

public interface DocumentationTool extends Tool, OptionChecker {
	DocumentationTask getTask(Writer out,
							  JavaFileManager fileManager,
							  DiagnosticListener<? super JavaFileObject> diagnosticListener,
							  Class<?> docletClass,
							  Iterable<String> options,
							  Iterable<? extends JavaFileObject> compilationUnits);

	StandardJavaFileManager getStandardFileManager(
			DiagnosticListener<? super JavaFileObject> diagnosticListener,
			Locale locale,
			Charset charset);

	enum Location implements JavaFileManager.Location {
		DOCUMENTATION_OUTPUT,

		DOCLET_PATH,

		TAGLET_PATH,

		SNIPPET_PATH;

		@Override
		public boolean isOutputLocation() {
			switch (this) {
			case DOCUMENTATION_OUTPUT:
				return true;
			default:
				return false;
			}
		}

		@Override
		public String getName() {
			return name();
		}
	}

	interface DocumentationTask extends Callable<Boolean> {
		void addModules(Iterable<String> moduleNames);

		void setLocale(Locale locale);

		@Override
		Boolean call();
	}

}
