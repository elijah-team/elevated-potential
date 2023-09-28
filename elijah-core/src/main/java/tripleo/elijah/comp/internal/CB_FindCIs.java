package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

class CB_FindCIs implements CB_Action {
	private final CompilationRunner   compilationRunner;
	private final List<CompilerInput> _inputs;
	private final CB_Output           o;

	@Contract(pure = true)
	public CB_FindCIs(final CompilationRunner aCompilationRunner, final List<CompilerInput> aInputs) {
		compilationRunner = aCompilationRunner;
		_inputs           = aInputs;
		o                 = compilationRunner.getCompilationEnclosure().getCB_Output(); // new CB_Output();
	}

	@Override
	public void execute(CB_Monitor aMonitor) {
		final List<CR_Action> crActionList = List_of(
				compilationRunner.cr_find_cis()
				, compilationRunner.cr_AlmostComplete() //hmm
		);

		for (final CR_Action action : crActionList) {
			action.attach(compilationRunner);
			action.execute(compilationRunner.getCrState(), o);
		}

		// TODO capture action outputs
		//  09/27 is that not being done above??
		aMonitor.reportSuccess(this, o);
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "FindCIs";
	}
}
