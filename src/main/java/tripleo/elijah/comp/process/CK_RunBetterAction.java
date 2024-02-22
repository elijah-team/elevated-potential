package tripleo.elijah.comp.process;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.Pipeline;
import tripleo.elijah.comp.graph.i.CK_Action;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_StepsContext;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.i.RuntimeProcess;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.OStageProcess;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CK_RunBetterAction implements CK_Action {
	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;
		final CR_State            crState = context.getState();
		final CB_Output           output  = context.getOutput();

		try {
			final ICompilationAccess   ca          = crState.ca();
			final Compilation0         compilation = ca.getCompilation();
			final CompilationEnclosure ce          = (CompilationEnclosure) ca.getCompilationEnclosure();

			ce.getPipelineAccessPromise().then(pa -> {
				final ProcessRecord  processRecord = pa.getProcessRecord();
				final RuntimeProcess process       = new OStageProcess(processRecord.ca());

				try {
					process.prepare();
				} catch (Exception aE) {
					//throw new RuntimeException(aE);
				}

				final Operation<Ok> res = process.run(compilation, new Pipeline.RP_Context_1(crState, output));

				if (res.mode() == Mode.FAILURE) {
					//Logger.getLogger(OStageProcess.class.getName()).log(Level.SEVERE, null, ex);

					final Exception ex = res.failure();
					ex.printStackTrace();

					//return Operation.failure(ex); eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
				}

				process.postProcess();
				ce.writeLogs();
			});
			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			aE.printStackTrace(); // TODO debug 07/26; 10/20 do we still want this?? also steps
			return Operation.failure(aE);
		}
	}
}