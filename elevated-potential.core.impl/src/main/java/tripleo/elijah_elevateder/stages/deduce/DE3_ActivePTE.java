package tripleo.elijah_elevateder.stages.deduce;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.reactive.*;

import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_fluffy.util.UnintendedUseException;
import tripleo.elijah_elevateder.comp.notation.*;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;
import tripleo.elijah_elevateder.stages.gen_fn.ProcTableEntry;
import tripleo.elijah_elevateder.stages.gen_generic.*;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_elevateder.work.EDL_WorkManager;
import tripleo.elijah_elevateder.world.i.WorldModule;

import java.util.*;
import java.util.function.*;

import static tripleo.elijah_fluffy.util.Helpers.*;

class DE3_ActivePTE implements DE3_Active {
	private final          DeduceTypes2    deduceTypes2;
	private final @NotNull ProcTableEntry  pte;
	private final          ClassInvocation ci;
	private final @NotNull List<Reactivable>                 ables;
	private final          DeduceTypes2.DeduceTypes2Injector __inj;

	public DE3_ActivePTE(final @NotNull DeduceTypes2 aDeduceTypes2, final @NotNull ProcTableEntry pte,
	                     final ClassInvocation classInvocation) {
		this.deduceTypes2 = aDeduceTypes2;
		this.pte          = pte;
		this.ci           = classInvocation;

		this.__inj = aDeduceTypes2._inj();

		this.ables = _inj().new_ArrayList__Ables();
	}

	private void __do_001(final @NotNull GenerateFiles generateC,
	                      final EvaClass node,
	                      final DeducePhase deducePhase,
	                      final GenerateResultSink resultSink,
	                      final @NotNull Eventual<GenerateResultEnv> efg) {
		efg.then(fg -> {
			final DeducePhase.GeneratedClasses classes = deducePhase.generatedClasses;
			final int                          size1   = classes.size();
			final GenerateResult               x       = generateC.resultsFromNodes(List_of(node), _inj().new_WorkManager(), resultSink, fg);
			final int                          size2   = classes.size();

			if (size2 > size1) {
				logProgress(3047, "" + (size2 - size1) + " results generated for " + node.identityString());
			} else {
				//assert size2 == 0;

				logProgress(3046, "only " +size2 +" results generated for " + node.identityString() /*+ "out of "+size1*/);
			}

			for (GenerateResultItem result : x.results()) {
				logProgress(3045, "" + result);
			}
		});
	}

	private DeduceTypes2.DeduceTypes2Injector _inj() {
		return __inj;
	}

	@Override
	public void add(final Reactivable aReactivable) {
		ables.add(aReactivable);
	}

	@Override
	public <T> void addListener(final Consumer<T> t) {
		throw new UnintendedUseException();
	}

	@NotNull
	private Eventual<GenerateResultEnv> getResultEnv(final @NotNull GenerateC generateC,
	                                                 final GenerateResultSink resultSink) {
		Eventual<GenerateResultEnv> R = new Eventual<>();

		// TODO isn't there an onFileGen somewhere?
		final GenerateResultEnv[] fg0 = {generateC.getFileGen()};

		assert fg0[0] != null;
		if (fg0[0] == null) {
			generateC._ce().getPipelineAccessPromise().then(pa -> {
				// FIXME as written, this will do nothing
				// TODO also highly suspicious that this is never called

//				final EIT_ModuleList moduleList = new EIT_ModuleList(/* List_of() */);

				EIT_ModuleList moduleList = null;

				var env = new GN_GenerateNodesIntoSinkEnv(
						List_of(), // !!
						new DefaultGenerateResultSink(pa),
						// !!
						ElLog_.Verbosity.VERBOSE,
						new Old_GenerateResult(),
						pa.getCompilationEnclosure()
				);

				final WorldModule mod = null; // pte.__gf.getFD().getContext().module();

				var tt = new GM_GenerateModuleRequest(new GN_GenerateNodesIntoSink(env), mod, env);
				var t  = new GM_GenerateModule(tt);
				fg0[0] = new GenerateResultEnv(resultSink,
											   new Old_GenerateResult(),
											   new EDL_WorkManager(),
											   new EDL_WorkList(),
											   t
				);

				R.resolve(fg0[0]);
			});
		} else {
			R.resolve(fg0[0]);
		}

		return R;
	}

	@Override
	public void join(final ReactiveDimension aDimension) {
		if (aDimension instanceof DeducePhase) {
			int y = 2;
		}
		if (aDimension instanceof GenerateC generateC) {
			if (pte.getClassInvocation() == null) {
				pte.onFunctionInvocation(x -> {
					int y = 2;
				}); // FIXME bug: points to `f'
			}

			// assert null != (pte.getClassInvocation());

			if (pte.getClassInvocation() != null)
				(pte.getClassInvocation()).resolvePromise().then(node -> {
					if (generateC.resultSink == null) {
						// assert false;
						generateC.resultSink = _inj().new_DefaultGenerateResultSink(deduceTypes2.phase.pa);
					}

					var resultSink = generateC.resultSink;

					var efg = getResultEnv(generateC, resultSink);

					final DeducePhase deducePhase = deduceTypes2._phase();

					__do_001(generateC, node, deducePhase, resultSink, efg);
				});
		}
	}

	private void logProgress(final int code, final String message) {
		var ce = deduceTypes2._phase().pa.getCompilationEnclosure();
//		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("" + code + " " + message);
		ce.logProgress(CompProgress.DeducePhase, Pair.of(code, message));
	}
}
