package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.Pipeline;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.GPipelineAccess;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.util.*;

public class CK_RunBetterAction implements CK_Action {
	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {

		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;
		final CR_State            crState = context.getState();
		final CB_Output           output  = context.getOutput();

		try {
			final ICompilationAccess ca             = crState.ca();
			final GPipelineAccess    pa             = ca.getCompilation().pa();
			final IPipelineAccess    pipelineAccess = (IPipelineAccess) pa;
			final ProcessRecord      processRecord  = pipelineAccess.getProcessRecord();
			final RuntimeProcess     process        = new OStageProcess(processRecord.ca(), processRecord);

			process.prepare();

			final Operation<Ok> res = process.run(ca.getCompilation(), new Pipeline.RP_Context_1(crState, output));

			if (res.mode() == Mode.FAILURE) {
				//Logger.getLogger(OStageProcess.class.getName()).log(Level.SEVERE, null, ex);

				final Exception ex = res.failure();
				ex.printStackTrace();

				return Operation.failure(ex);
			}

			process.postProcess();
			processRecord.writeLogs();

			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			aE.printStackTrace(); // TODO debug 07/26; 10/20 do we still want this?? also steps
			return Operation.failure(aE);
		}
	}
}
