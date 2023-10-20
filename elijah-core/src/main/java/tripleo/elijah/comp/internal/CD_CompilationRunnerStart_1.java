package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.nextgen.CK_DefaultStepRunner;
import tripleo.elijah.util.*;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class CD_CompilationRunnerStart_1 implements CD_CompilationRunnerStart {
	private CK_Steps                steps;
	private List<CK_Action>         stepActions = new ArrayList<>();
	private List<CR_Action>         crActionList;
	private CK_AbstractStepsContext stepContext;

	@Override
	public void start(final @NotNull CompilerInstructions aRootCI,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		this.stepContext = new CD_CRS_StepsContext(crState, out);

		final @NotNull CompilationRunner             cr            = crState.runner();
		final @NotNull Compilation                   compilation   = cr._accessCompilation();
		final @NotNull IPipelineAccess               pa            = compilation.getCompilationEnclosure().getPipelineAccess();
		final @NotNull Compilation.CompilationConfig cfg           = compilation.cfg();
		final @NotNull List<CompilerInput>           compilerInput = pa.getCompilerInput();
		final @NotNull IProgressSink                 progressSink  = cr.getProgressSink();

		final CompilerBeginning beginning = new CompilerBeginning(compilation, aRootCI, compilerInput, progressSink, cfg);

		initialiseCrActions(crState, beginning);
		initialiseSteps();

		final @NotNull List<Operation<Ok>> crActionResultList = new ArrayList<>(crActionList.size());

		for (final CR_Action action : crActionList) {
			action.attach(crState.runner());

			final Operation<Ok> res = action.execute(crState, out);

			// FIXME 10/20 dlog this
			crActionResultList.add(res);
		}

		// TODO execute should do this automatically
		for (int i = 0; i < crActionResultList.size(); i++) {
			final Operation<Ok> act5ionResult = crActionResultList.get(i);

			out.logProgress(new CodedOperationDiagnostic<Ok>(5959, "[CR_Action]", act5ionResult));
		}

		final CK_Monitor monitor = ((CompilationImpl) compilation).getDefaultMonitor();
		CK_DefaultStepRunner.runStepsNow(steps, stepContext, monitor);
	}

	protected void ___start(final @NotNull CR_State crState,
							final @NotNull CompilerBeginning beginning,
							final @NotNull CB_Output out) {
		initialiseCrActions(crState, beginning);
		initialiseSteps();

		final @NotNull List<Operation<Ok>> crActionResultList = new ArrayList<>(crActionList.size());

		for (final CR_Action action : crActionList) {
			action.attach(crState.runner());

			final Operation<Ok> res = action.execute(crState, out);

			// FIXME 10/20 dlog this
			crActionResultList.add(res);
		}

		// TODO execute should do this automatically
		for (int i = 0; i < crActionResultList.size(); i++) {
			final Operation<Ok> act5ionResult = crActionResultList.get(i);

			out.logProgress(new CodedOperationDiagnostic<Ok>(5959, "[CR_Action]", act5ionResult));
		}

		NotImplementedException.raise_stop();
	}

	private void initialiseCrActions(final @NotNull CR_State crState, final CompilerBeginning beginning) {
		final CR_ProcessInitialAction f2 = new CR_ProcessInitialAction(beginning); // TODO pointless
		final CR_AlmostComplete       f3 = crState.runner().cr_AlmostComplete();
		final CR_RunBetterAction      f4 = new CR_RunBetterAction();

		// hmm
		//final @NotNull List<CR_Action> crActionList = List_of(f2, f4);
		crActionList = List_of(f2, f3, f4);
	}

	private void initialiseSteps() {
		for (final CR_Action action : crActionList) {
			stepActions.add(new CD_CRS_CK_Action(action));
		}

		steps = () -> stepActions;
	}

	private static class CD_CRS_StepsContext extends CK_AbstractStepsContext {
		@Getter
		private final CR_State  state;
		@Getter
		private final CB_Output output;

		public CD_CRS_StepsContext(final CR_State aState, final CB_Output aOutput) {
			state  = aState;
			output = aOutput;
		}
	}

	private static class CD_CRS_CK_Action implements CK_Action {
		private final CR_Action action;

		public CD_CRS_CK_Action(final CR_Action aAction) {
			action = aAction;
		}

		@Override
		public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
			final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;
			return action.execute(context.getState(), context.getOutput());
		}
	}
}
