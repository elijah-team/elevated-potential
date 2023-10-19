/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import com.google.common.collect.*;
import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.AccessBus.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;
import tripleo.elijah.util.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 8/21/21 10:19 PM
 */
public class WritePipeline extends PipelineMember implements Consumer<Supplier<GenerateResult>>, AB_GenerateResultListener {
	private final          DeferredObject<GenerateResult, Void, Void> generateResultPromise = new DeferredObject<>();
	private final @NotNull WritePipelineSharedState                   st;
	private final @NotNull CompletedItemsHandler                      cih;
	private final @NotNull DoubleLatch<GenerateResult> latch;

	private WP_Flow.OPS ops;

	private final CK_Monitor monitor = new CK_Monitor() {
		@Override
		public void reportSuccess() {
			int y=2;
		}

		@Override
		public void reportFailure() {
			int y=2;
		}
	};

	public WritePipeline(final @NotNull IPipelineAccess pa) {
		st = new WritePipelineSharedState(pa);

		// computed

		// created
		latch = new DoubleLatch<GenerateResult>(gr -> doubleLatch_GenerateResult(gr, pa));

		// state
		st.mmb = ArrayListMultimap.create();
		st.lsp_outputs = ArrayListMultimap.create();
		st.grs = pa.getGenerateResultSink();

		// ??
		st.sys = new ElSystem(false, st.c, this::createOutputStratgy);

		cih = new CompletedItemsHandler(st);

		pa.getAccessBus().subscribe_GenerateResult(this::gr_slot);
		pa.getAccessBus().subscribe_GenerateResult(generateResultPromise::resolve);

		pa.setWritePipeline(this);

		// st.outputs = pa.getOutputs();
	}

	private void doubleLatch_GenerateResult(final GenerateResult gr, final @NotNull IPipelineAccess pa) {
		st.setGr(gr);

		final WP_Individual_Step wpis_go = new WPIS_GenerateOutputs();
		final WP_Individual_Step wpis_wi = new WPIS_WriteInputs();
		final WP_Individual_Step wpis_wb = new WPIS_WriteBuffers(WritePipeline.this);

		// TODO: Do something with op, like set in {@code pa} to proceed to next pipeline
		// TODO 10/18 this is a CK_Steps
		final List<WP_Individual_Step> pises = List_of(wpis_go, wpis_wi, wpis_wb);
		final WP_Flow                  f     = new WP_Flow(WritePipeline.this, pa, pises);


		// comment
		//ops = f.act();

		CK_Steps steps = new CK_Steps() {

			// TODO 10/18 I'm sure there is a better way to do this
			@Override
			public List<CK_Action> steps() {
				return pises.stream()
						.map(p -> (CK_Action)p)
						.collect(Collectors.toList());
			}
		};

		// comment
		f.sc = new WP_State_Control_1();

		CK_StepsContext stepsContext = f;
		pa.runStepsNow(steps, stepsContext);

		pa.finishPipeline(WritePipeline.this, ops);
	}

	//WritePipeline_CK_StepsContext stepsContext = new WritePipeline_CK_StepsContext();

	@Override
	public void accept(final @NotNull Supplier<GenerateResult> aGenerateResultSupplier) {
		//final GenerateResult gr = aGenerateResultSupplier.get();
		int y = 2;
	}

	public @NotNull Consumer<Supplier<GenerateResult>> consumer() {
		if (false) {
			return new Consumer<Supplier<GenerateResult>>() {
				@Override
				public void accept(final Supplier<GenerateResult> aGenerateResultSupplier) {
					// final GenerateResult gr = aGenerateResultSupplier.get();
				}
			};
		}

		return (x) -> {
		};
	}

	@NotNull
	OutputStrategy createOutputStratgy() {
		final OutputStrategy os = new OutputStrategy();
		os.per(OutputStrategy.Per.PER_CLASS); // TODO this needs to be configured per lsp

		return os;
	}

	@Override
	public void gr_slot(final @NotNull GenerateResult gr1) {
		Objects.requireNonNull(gr1);
		latch.notifyData(gr1);
		gr1.subscribeCompletedItems(cih.observer());
	}

	@Override
	public void run(final CR_State aSt, final CB_Output aOutput) throws Exception {
		latch.notifyLatch(true);
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}

	public DeferredObject<GenerateResult, Void, Void> getGenerateResultPromise() {
		return generateResultPromise;
	}

	public WritePipelineSharedState getSt() {
		return st;
	}

	private static class CompletedItemsHandler {
		// README debugging purposes
		private final List<GenerateResultItem> abs = new ArrayList<>();
		private final Multimap<Dependency, GenerateResultItem> gris = ArrayListMultimap.create();
		private final @NotNull ElLog LOG;
		private final WritePipelineSharedState sharedState;
		private Observer<GenerateResultItem> observer;

		public CompletedItemsHandler(final WritePipelineSharedState aSharedState) {
			sharedState = aSharedState;

			final ElLog.Verbosity verbosity = sharedState.c.cfg().silent ? ElLog.Verbosity.SILENT
					: ElLog.Verbosity.VERBOSE;

			LOG = new ElLog("(WRITE-PIPELINE)", verbosity, "(write-pipeline)");

			sharedState.pa.addLog(LOG);
		}

		public void addItem(final @NotNull GenerateResultItem ab) {
			NotImplementedException.raise();

			// README debugging purposes
			abs.add(ab);

			LOG.info("-----------=-----------=-----------=-----------=-----------=-----------");
			LOG.info("GenerateResultItem >> " + ab.jsonString());
			LOG.info("abs.size >> " + abs.size());

			final Dependency dependency = ab.getDependency();

			LOG.info("ab.getDependency >> " + dependency.jsonString());

			// README debugging purposes
			final DependencyRef dependencyRef = dependency.getRef();

			LOG.info("dependencyRef >> " + (dependencyRef != null ? dependencyRef.jsonString() : "null"));

			if (dependencyRef == null) {
				gris.put(dependency, ab);
			} else {
				final String output = ((CDependencyRef) dependencyRef).getHeaderFile();

				LOG.info("CDependencyRef.getHeaderFile >> " + output);

				sharedState.mmb.put(output, ab.buffer());
				sharedState.lsp_outputs.put(ab.lsp().getInstructions(), output);
				for (GenerateResultItem generateResultItem : gris.get(dependency)) {
					final String output1 = generateResultItem.output();
					sharedState.mmb.put(output1, generateResultItem.buffer());
					sharedState.lsp_outputs.put(generateResultItem.lsp().getInstructions(), output1);
				}

				// for (Map.Entry<Dependency, Collection<GenerateResultItem>> entry :
				// gris.asMap().entrySet()) {
				// System.out.println(entry.getKey().jsonString());
				// System.out.println(entry.getValue());
				// }

				/*
				 * if (gris.containsKey(dependency)) System.out.println("*** 235 yes"); else
				 * System.out.println("*** 235 no");
				 */

				gris.removeAll(dependency);
			}

			LOG.info("-----------=-----------=-----------=-----------=-----------=-----------");
		}

		public void completeSequence() {
			final @NotNull GenerateResult generateResult = sharedState.getGr();

			generateResult.outputFiles((final @NotNull Map<String, OutputFileC> outputFiles) -> {
				// 08/13 System.err.println("252252"); // 06/16
			});
		}

		@Contract(mutates = "this")
		public @NotNull Observer<GenerateResultItem> observer() {
			if (observer == null) {
				observer = new Observer<GenerateResultItem>() {
					@Override
					public void onComplete() {
						completeSequence();
					}

					@Override
					public void onError(@NonNull Throwable e) {
					}

					@Override
					public void onNext(@NonNull @NotNull GenerateResultItem ab) {
						addItem(ab);
					}

					@Override
					public void onSubscribe(@NonNull Disposable d) {
					}
				};
			}

			return observer;
		}
	}
}

//
//
//
