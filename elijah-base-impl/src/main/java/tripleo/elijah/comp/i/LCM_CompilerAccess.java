package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.internal.CompilationImpl;

public interface LCM_CompilerAccess {
	Compilation c();

	ICompilationRunner cr();

	CompilationImpl.CompilationConfig cfg();
}
