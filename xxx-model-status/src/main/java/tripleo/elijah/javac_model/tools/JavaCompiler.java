package tripleo.elijah.javac_model.tools;

import javax.annotation.processing.Processor;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.Callable;

public interface JavaCompiler extends Tool, OptionChecker {

	CompilationTask getTask(Writer out,
							JavaFileManager fileManager,
							DiagnosticListener<? super JavaFileObject> diagnosticListener,
							Iterable<String> options,
							Iterable<String> classes,
							Iterable<? extends JavaFileObject> compilationUnits);

	StandardJavaFileManager getStandardFileManager(
			DiagnosticListener<? super JavaFileObject> diagnosticListener,
			Locale locale,
			Charset charset);

	interface CompilationTask extends Callable<Boolean> {
		void addModules(Iterable<String> moduleNames);

		void setProcessors(Iterable<? extends Processor> processors);

		void setLocale(Locale locale);

		@Override
		Boolean call();
	}
}
