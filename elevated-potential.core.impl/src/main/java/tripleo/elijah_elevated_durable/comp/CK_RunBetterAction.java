package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.comp_driven.CD_CRS_StepsContext;
import tripleo.elijah_elevated_durable.comp_process.OStageProcess;
import tripleo.elijah_elevated_durable.pipelines.Pipeline;
import tripleo.elijah_elevateder.comp.i.ProcessRecord;
import tripleo.elijah_fluffy.util.*;

public class CK_RunBetterAction implements CK_Action {
	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;
		final CR_State            crState = context.getState();
		final CB_Output           output  = context.getOutput();

		try {
			final ICompilationAccess   ca             = crState.ca();
			final Compilation0         compilation    = ca.getCompilation();
			final CompilationEnclosure ce             = (CompilationEnclosure) ca.getCompilationEnclosure();

			ce.getPipelineAccessPromise().then(pipelineAccess -> {
				final ProcessRecord  processRecord = pipelineAccess.getProcessRecord();
				final RuntimeProcess process       = new OStageProcess(processRecord.ca());

				try {
					process.prepare();
				} catch (Exception aE) {
					//throw new RuntimeException(aE);
				}

				final Operation<Ok> res = process.run(compilation, new Pipeline.RP_Context_1(crState, output));

				if (res.mode() == Mode.FAILURE) {
					//Logger.getLogger(OStageProcess.class.getName()).log(Level.SEVERE, null, ex);

					final var ex = res.failure();
					ex.printStackTrace();

					//return Operation.failure(ex); eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
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
