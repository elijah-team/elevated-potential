package tripleo.elijah.comp.i;

import tripleo.elijah.comp.internal.CompilerDriver;

import java.util.List;

public interface ICompilationBus {
	void add(CB_Action aCBAction);

	void add(CB_Process aProcess);

	IProgressSink defaultProgressSink();

	CompilerDriver getCompilerDriver();

	void inst(ILazyCompilerInstructions aLazyCompilerInstructions);

	void option(CompilationChange aChange);

	List<CB_Process> processes();
}
