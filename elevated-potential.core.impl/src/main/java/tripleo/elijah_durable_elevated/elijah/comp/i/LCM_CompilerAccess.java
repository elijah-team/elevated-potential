package tripleo.elijah_durable_elevated.elijah.comp.i;

import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.comp.internal.CompilationImpl;

public interface LCM_CompilerAccess {
	Compilation c();

	ICompilationRunner cr();

	CompilationImpl.CompilationConfig cfg();
}
