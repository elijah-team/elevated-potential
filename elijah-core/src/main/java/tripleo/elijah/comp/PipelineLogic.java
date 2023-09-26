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
import io.reactivex.rxjava3.annotations.NonNull;
import lombok.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

import java.util.*;
import java.util.function.*;

/**
 * Created 12/30/20 2:14 AM
 */
public class PipelineLogic implements @NotNull EventualRegister {
	public final @NotNull  DeducePhase              dp;
	public final @NotNull  GeneratePhase            generatePhase;
	private final @NonNull List<ElLog>              elLogs = new LinkedList<>();
//	private final @NonNull EIT_ModuleList           mods   = new EIT_ModuleList();
	private final @NonNull ModuleCompletableProcess mcp    = new ModuleCompletableProcess();
	private final @NonNull ModMap                   modMap = new ModMap();
	private final @NonNull IPipelineAccess          pa;
	@Getter
	private final @NonNull ElLog.Verbosity verbosity;
	private List<Eventual<?>> _eventuals = new ArrayList<>();

	public PipelineLogic(final IPipelineAccess aPa, final @NotNull ICompilationAccess ca) {
		pa = aPa;

		// TODO annotation time, or use clj
		pa.install_notate(Provenance.PipelineLogic__nextModule, GN_PL_Run2.class, GN_PL_Run2_Env.class);

		ca.setPipelineLogic(this);
		verbosity     = ca.testSilence();
		generatePhase = new GeneratePhase(verbosity, pa, this);
		dp            = new DeducePhase(ca, pa, this);

		pa.getCompilationEnclosure().addModuleListener(new ModuleListener() {
			@Override
			public void close() {
				NotImplementedException.raise_stop();
			}

			@Override
			public void listen(final WorldModule module) {
				module.getErq().then(rq -> {
					final OS_Module         mod = module.module();
					final GenerateFunctions gfm = getGenerateFunctions(mod);

					gfm.generateFromEntryPoints(rq);

					// ---

					modMap.then(mod, (final Eventual<DeducePhase.GeneratedClasses> eventual) -> {
						final DeducePhase.@NotNull GeneratedClasses lgc = dp.generatedClasses;
						eventual.resolve(lgc);
					});
				});
			}
		});
	}

	public ModuleCompletableProcess _mcp() {
		return mcp;
	}

	public @NonNull IPipelineAccess _pa() {
		return pa;
	}

	public void addLog(ElLog aLog) {
		elLogs.add(aLog);
	}

	@Override
	public void checkFinishEventuals() {
		int y = 0;
		for (Eventual<?> eventual : _eventuals) {
			if (eventual.isResolved()) {
			} else {
				System.err.println("[PipelineLogic::checkEventual] failed for " + eventual.description());
			}
		}
	}

	@NotNull
	public GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod) {
		return generatePhase.getGenerateFunctions(mod);
	}

	public List<ElLog> getLogs() {
		return elLogs;
	}

	public Eventual<DeducePhase.GeneratedClasses> handle(final GN_PL_Run2.@NotNull GenerateFunctionsRequest rq) {
		final OS_Module          mod = rq.mod();
		final DefaultWorldModule wm  = rq.worldModule();

		assert wm != null;

		final Eventual<DeducePhase.GeneratedClasses> p = new Eventual<>();
		p.register(this);
		modMap.put(mod, p);

		return p;
	}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}

	class ModMap {
		private final Map<OS_Module, Eventual<DeducePhase.GeneratedClasses>>                    modMap = new HashMap<>();
		private final Multimap<OS_Module, DoneCallback<Eventual<DeducePhase.GeneratedClasses>>> mmme   = ArrayListMultimap
				.create();

		public void put(OS_Module mod, Eventual<DeducePhase.GeneratedClasses> p) {
			modMap.put(mod, p);
			var x = mmme.get(mod);
			for (DoneCallback<Eventual<DeducePhase.GeneratedClasses>> callback : x) {
				callback.onDone(p);
			}
		}

		public void then(OS_Module mod, DoneCallback<Eventual<DeducePhase.GeneratedClasses>> p) {
			mmme.put(mod, p);

			var x = modMap.get(mod);
			if (x != null) {
				p.onDone(x);
			} else {
				var e = new Eventual<DeducePhase.GeneratedClasses>();
				e.then(lgc -> p.onDone(e));
				modMap.put(mod, e);
			}
		}
	}

	public final class ModuleCompletableProcess implements CompletableProcess<WorldModule> {
		@Override
		public void add(final @NotNull WorldModule aWorldModule) {
			// System.err.printf("7070 %s %d%n", mod.getFileName(), mod.entryPoints.size());

			final CompilationEnclosure  ce            = pa.getCompilationEnclosure();
			final Consumer<WorldModule> worldConsumer = ce::noteAccept; // FIXME not data...
			final GN_PL_Run2_Env        pl_run2       = new GN_PL_Run2_Env(PipelineLogic.this, aWorldModule, ce, worldConsumer);

			pa.notate(Provenance.PipelineLogic__nextModule, pl_run2);
		}

		@Override
		public void complete() {
			dp.finish();
		}

		@Override
		public void error(final Diagnostic d) {
//			throw new UnintendedUseException();
		}

		@Override
		public void preComplete() {

		}

		@Override
		public void start() {

		}
	}
}

//
//
//
