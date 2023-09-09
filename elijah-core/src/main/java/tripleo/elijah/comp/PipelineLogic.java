/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import io.reactivex.rxjava3.annotations.NonNull;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.Provenance;
import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.comp.notation.GN_PL_Run2_Env;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.gen_fn.GeneratePhase;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created 12/30/20 2:14 AM
 */
public class PipelineLogic implements @NotNull EventualRegister {
	public final @NotNull  DeducePhase                                            dp;
	public final @NotNull  GeneratePhase                                          generatePhase;
	private final @NonNull List<ElLog>                                            elLogs     = new LinkedList<>();
	private final @NonNull EIT_ModuleList                                         mods       = new EIT_ModuleList();
	private final @NonNull ModuleCompletableProcess                               mcp        = new ModuleCompletableProcess();
	private final @NonNull Map<OS_Module, Eventual<DeducePhase.GeneratedClasses>> modMap     = new HashMap<>();
	private final @NonNull IPipelineAccess                                        pa;
	@Getter
	private final @NonNull ElLog.Verbosity                                        verbosity;
	private                List<Eventual<?>>                                      _eventuals = new ArrayList<>();


	public PipelineLogic(final IPipelineAccess aPa, final @NotNull ICompilationAccess ca) {
		pa = aPa;

		// TODO annotation time, or use clj
		pa.install_notate(Provenance.PipelineLogic__nextModule, GN_PL_Run2.class, GN_PL_Run2_Env.class);

		ca.setPipelineLogic(this);
		verbosity     = ca.testSilence();
		generatePhase = new GeneratePhase(verbosity, pa, this);
		dp            = new DeducePhase(ca, pa, this);

		pa.getCompilationEnclosure().addModuleListener(new CompilationEnclosure.ModuleListener() {
			@Override
			public void listen(final WorldModule module) {
				final OS_Module         mod = module.module();
				final GenerateFunctions gfm = getGenerateFunctions(mod);

				final GN_PL_Run2.GenerateFunctionsRequest rq = module.rq();
				if (rq != null) {
					gfm.generateFromEntryPoints(rq);

					// ---

					final Eventual<DeducePhase.GeneratedClasses> eventual = modMap.get(mod);

					if (eventual != null) {
						final DeducePhase.@NotNull GeneratedClasses lgc = dp.generatedClasses;
						eventual.resolve(lgc);
					} else {
						NotImplementedException.raise_stop();
					}
				}
			}

			@Override
			public void close() {
				NotImplementedException.raise_stop();
			}
		});
	}

	@NotNull
	public GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod) {
		return generatePhase.getGenerateFunctions(mod);
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

	public void addLog(ElLog aLog) {
		elLogs.add(aLog);
	}

	public void addModule(WorldModule m) {
		mods.add(m);
	}

	public @NotNull EIT_ModuleList mods() {
		return mods;
	}

	public List<ElLog> getLogs() {
		return elLogs;
	}

	public ModuleCompletableProcess _mcp() {
		return mcp;
	}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
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

	public @NonNull IPipelineAccess _pa() {
		return pa;
	}

	public final class ModuleCompletableProcess implements CompletableProcess<WorldModule> {

		@Override
		public void add(final @NotNull WorldModule aWorldModule) {
			//System.err.printf("7070 %s %d%n", mod.getFileName(), mod.entryPoints.size());

			final CompilationEnclosure  ce            = pa.getCompilationEnclosure();
			final Consumer<WorldModule> worldConsumer = ce::noteAccept; // FIXME not data...
			final GN_PL_Run2_Env pl_run2 = new GN_PL_Run2_Env(PipelineLogic.this, aWorldModule, ce, worldConsumer);

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
