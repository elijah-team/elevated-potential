package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

public class EDL_ICompilationAccess3 implements ICompilationAccess3 {
	private final CompilationImpl compilation;

	public EDL_ICompilationAccess3(final CompilationImpl aCompilation) {
		this.compilation = aCompilation;
	}

	@Override
	public Compilation getComp() {
		return compilation;
	}

	@Override
	public boolean getSilent() {
		return getComp().cfg().silent;
	}

	@Override
	public void addLog(final ElLog aLog) {
		getComp().getCompilationEnclosure().addLog(aLog);
	}

	@Override
	public List<ElLog> getLogs() {
		return getComp().getCompilationEnclosure().getLogs();
	}

	@Override
	public void writeLogs(final boolean aSilent) {
		assert !aSilent;
		getComp().getCompilationEnclosure().writeLogs();
	}

	@Override
	public PipelineLogic getPipelineLogic() {
		return getComp().getCompilationEnclosure().getPipelineLogic();
	}
}
