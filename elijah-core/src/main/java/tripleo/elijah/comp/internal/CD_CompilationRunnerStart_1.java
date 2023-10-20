package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.util.*;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class CD_CompilationRunnerStart_1 implements CD_CompilationRunnerStart {
	private CK_Steps                steps;
	private List<CK_Action>         stepActions = new ArrayList<>();
	private List<CR_Action>         crActionList;
	@Getter
	private List<Operation<Ok>>     crActionResultList;
	private CK_AbstractStepsContext stepContext;

	@Override
	public void start(final @NotNull CompilerInstructions aRootCI,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		this.stepContext = new CD_CRS_StepsContext(crState, out);

		//final @NotNull CompilationRunner             cr                   = crState.runner();
		final @NotNull CompilationEnclosure compilationEnclosure = crState.getCompilationEnclosure();
		//final @NotNull Compilation          compilation          = compilationEnclosure.getCompilation();
		//final @NotNull IPipelineAccess               pa                   = compilationEnclosure.getPipelineAccess();
		//final @NotNull Compilation.CompilationConfig cfg                  = compilation.cfg();
		//final @NotNull List<CompilerInput>           compilerInput        = pa.getCompilerInput();
		//final @NotNull IProgressSink                 progressSink         = cr.getProgressSink();
		final @NotNull CK_Monitor monitor = compilationEnclosure.getDefaultMonitor();

		initialiseCrActions(crState, aRootCI, monitor);
		initialiseSteps();

		crActionResultList = new ArrayList<>(crActionList.size());

		//for (final CR_Action action : crActionList) {
		//	action.attach(crState.runner());
		//
		//	final Operation<Ok> res = action.execute(crState, out);
		//
		//	// FIXME 10/20 dlog this
		//	crActionResultList.add(res);
		//}
		//
		//// TODO execute should do this automatically
		//for (int i = 0; i < crActionResultList.size(); i++) {
		//	final Operation<Ok> act5ionResult = crActionResultList.get(i);
		//
		//	out.logProgress(new CodedOperationDiagnostic<Ok>(5959, "[CR_Action]", act5ionResult));
		//}

		compilationEnclosure.runStepsNow(steps, stepContext);
	}

	private void initialiseCrActions(final @NotNull CR_State crState, final @NotNull CompilerInstructions aRootCI, final CK_Monitor monitor) {
		final CR_CK_ActionWrapper f2 = new CR_CK_ActionWrapper(this, new CK_ProcessInitialAction(aRootCI), monitor);
		final CR_AlmostComplete   f3 = crState.runner().cr_AlmostComplete();
		final CR_RunBetterAction  f4 = new CR_RunBetterAction();

		// hmm
		//final @NotNull List<CR_Action> crActionList = List_of(f2, f4);
		crActionList = List_of(f2, f3, f4);
	}

	private void initialiseSteps() {
		for (final CR_Action action : crActionList) {
			stepActions.add(new CD_CRS_CK_Action(this, action));
		}

		steps = () -> stepActions;
	}

}

//
//
//
