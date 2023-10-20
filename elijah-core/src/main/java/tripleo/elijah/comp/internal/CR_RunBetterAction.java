package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Stages;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.GPipelineAccess;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CR_RunBetterAction implements CR_Action {
	public enum StageToRuntime {
		;

		public static @NotNull RuntimeProcesses get(final @NotNull IPipelineAccess aPa) {
			final ICompilationAccess ca = aPa.getCompilationEnclosure().getCompilationAccess();
			return get((Stages) ca.getStage(), ca, aPa);
		}

		@Contract("_, _, _, _ -> new")
		@NotNull
		public static RuntimeProcesses get(final @NotNull Stages stage, final @NotNull ICompilationAccess ca,
				final @NotNull IPipelineAccess aPa) {
			final ProcessRecord processRecord = aPa.getProcessRecord();
			final RuntimeProcesses r = new RuntimeProcesses(ca, processRecord);

			r.add(stage.getProcess(ca, processRecord));

			return r;
		}
	}

	@Override
	public void attach(final @NotNull CompilationRunner cr) {

	}

	@Override
	public @NotNull Operation<Ok> execute(final @NotNull CR_State st, final CB_Output aO) {
		try {
			final ICompilationAccess ca = st.ca();
			final GPipelineAccess    pa = ca.getCompilation().pa();
			final RuntimeProcesses   rt = StageToRuntime.get((IPipelineAccess) pa);
			rt.run_better(st, aO);

			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			aE.printStackTrace(); // TODO debug 07/26; 10/20 do we still want this?? also steps
			return Operation.failure(aE);
		}
	}

	@Override
	public @NotNull String name() {
		return "run better";
	}
}
