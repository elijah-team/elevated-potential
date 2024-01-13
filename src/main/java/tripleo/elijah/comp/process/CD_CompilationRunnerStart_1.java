package tripleo.elijah.comp.process;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.CR_Action;
import tripleo.elijah.comp.internal.CD_CompilationRunnerStart;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.util.*;
import tripleo.elijah.util2.UnintendedUseException;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class CD_CompilationRunnerStart_1 implements CD_CompilationRunnerStart {
	private       CK_Steps        steps;
	private final List<CK_Action> stepActions = new ArrayList<>();

	@Getter
	private List<Operation<Ok>>     crActionResultList;
	@Getter
	private CK_AbstractStepsContext stepContext;

	@Override
	public void start(final @NotNull CompilerInstructions aRootCI,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		//final @NotNull CK_Monitor monitor = compilationEnclosure.getDefaultMonitor();

		stepContext = new CD_CRS_StepsContext(crState, out);

		// TODO 10/20 remove k2 ??
		final CK_ProcessInitialAction k2 = new CK_ProcessInitialAction(aRootCI);
		final CK_AlmostComplete       k3 = new CK_AlmostComplete();
		final CK_RunBetterAction      k4 = new CK_RunBetterAction();

		stepActions.addAll(List_of(k2, k3, k4));

		final List<CR_Action> crActionList = new ArrayList<>();

		for (final CR_Action action : crActionList) {
			stepActions.add(new CD_CRS_CK_Action(this, action));
		}

		steps = () -> stepActions;

		crActionResultList = new ArrayList<>(crActionList.size());

		final @NotNull CompilationEnclosure compilationEnclosure = crState.getCompilationEnclosure();

		compilationEnclosure.getCompilation().contribute(new CPX_RunStepsContribution(steps, stepContext));

		compilationEnclosure.getCompilation().signals()
				.subscribeRunStepLoop(new CPX_RunStepLoop() {
					@Override
					public void notify_CPX_RunStepLoop(final Ok ok) {
						throw new UnintendedUseException("breakpoint");
					}
				});
	}

	public List<Operation<Ok>> getCrActionResultList() {
		// 24/01/04 back and forth
		return this.crActionResultList;
	}

}

//
//
//
