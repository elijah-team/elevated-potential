package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

public class EDL_ICompilationAccess3 implements ICompilationAccess3 {
	private final CompilationImpl a_c;

	public EDL_ICompilationAccess3(final CompilationImpl a_c) {
		this.a_c = a_c;
	}

	@Override
	public Compilation getComp() {
		return a_c;
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
