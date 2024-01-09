package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.IO;

public interface CompilationClosure {
	ErrSink errSink();

	Compilation0 getCompilation();

	IO io();
}
