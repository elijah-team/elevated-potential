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

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class CD_CompilationRunnerStart_1 implements CD_CompilationRunnerStart {

	@Getter
	private List<Operation<Ok>>     crActionResultList;
	@Getter
	private CK_AbstractStepsContext stepContext;

	@Override
	public void start(final @NotNull CompilerInstructions aRootCI,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		stepContext = new CD_CRS_StepsContext(crState, out);

		final List<CK_Action> actionList  = getCrActions(aRootCI);

		crActionResultList = new ArrayList<>(actionList.size());

		final @NotNull CompilationEnclosure compilationEnclosure = crState.getCompilationEnclosure();
		compilationEnclosure.contribute(new CPX_RunStepsContribution(()->actionList, stepContext));
		compilationEnclosure.signals()
				.signalRunStepLoop(compilationEnclosure.getCompilation().getRootCI());
//		;ubscribeRunStepLoop(new CPX_RunStepLoop() {
//					@Override
//					public void notify_CPX_RunStepLoop(final Ok ok) {
//						throw new UnintendedUseException("breakpoint");
//					}
//				});
	}

	private List<CK_Action> getCrActions(final @NotNull CompilerInstructions aRootCI) {
		// TODO 10/20 remove k2 ??
		final CK_ProcessInitialAction k2 = new CK_ProcessInitialAction(aRootCI);
		final CK_AlmostComplete       k3 = new CK_AlmostComplete();
		final CK_RunBetterAction      k4 = new CK_RunBetterAction();

		final List<CK_Action> al = List_of(k2, k3, k4);

		return al;
	}

	public void addCrActionResult(final Operation<Ok> aResult, final CR_Action aAction, final CD_CRS_StepsContext aContext) {
		crActionResultList.add(aResult);
	}
}

//
//
//
