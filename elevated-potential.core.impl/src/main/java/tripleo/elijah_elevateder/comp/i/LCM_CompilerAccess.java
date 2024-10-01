package tripleo.elijah_elevateder.comp.i;

import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;

public interface LCM_CompilerAccess {
	Compilation c();

	ICompilationRunner cr();

	EDL_Compilation.CompilationConfig cfg();
}
