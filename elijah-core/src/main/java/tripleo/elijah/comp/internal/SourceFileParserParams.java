package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.specs.*;

import java.io.*;

public record SourceFileParserParams(/* @NotNull */
		CompilerInput input, @NotNull File f, @NotNull String file_name, @NotNull CompilationClosure cc) {
	public EzSpec getEzSpec() throws FileNotFoundException {
		final String             f                  = this.file_name();
		final File               file               = this.f();
		final CompilationClosure compilationClosure = this.cc();

		final InputStream        s                  = compilationClosure.io().readFile(file);

		var y = new EzSpec(f, s, file);

		return y;
	}
}
