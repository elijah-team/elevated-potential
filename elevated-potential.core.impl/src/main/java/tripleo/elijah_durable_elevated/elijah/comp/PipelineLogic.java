/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.comp;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.reactivex.rxjava3.annotations.NonNull;
import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.*;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.g.GPipelineLogic;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stages.logging.ElLog.Verbosity;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.comp.internal.Provenance;
import tripleo.elijah_durable_elevated.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah_durable_elevated.elijah.comp.notation.GN_PL_Run2_Env;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.util.Helpers0;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;
import tripleo.elijah_durable_elevated.elijah.world.impl.DefaultWorldModule;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created 12/30/20 2:14 AM
 */
public class PipelineLogic implements EventualRegister, GPipelineLogic {
	public final @NotNull  GeneratePhase            generatePhase;
	public final @NotNull  DeducePhase              dp;
	final @NonNull         ModMap                   modMap     = new ModMap();
	private final          ICompilationAccess       ca;
	private final @NonNull ModuleCompletableProcess mcp        = new ModuleCompletableProcess();
	private final @NonNull IPipelineAccess          pa;
	private final          List<Eventual<?>>        _eventuals = new ArrayList<>();
	//	private final @NonNull EIT_ModuleList           mods   = new EIT_ModuleList();
	public                 GDM_Pipeline             pl         = new GDM_Pipeline() {
		@Override
		public GDM_Product submit(final Object aMod) {
			final PipelineLogic pipelineLogic = PipelineLogic.this;

			CompletableProcess<GDP_Module> result = null;
			if (aMod instanceof WorldModule wm) {
				final WorldModule   mod = wm;
				final List<EvaNode> lgc = new ArrayList<>();
				pipelineLogic.dp.deduceModule(mod,
											  Collections.unmodifiableList(lgc),
											  pipelineLogic.getVerbosity());
				result = new GDP_ModuleCompletableProcess(lgc);
				return GDM_Product.of(result);
			}
			return null; // maybe null/void cp??
		}
	};

	public PipelineLogic(final IPipelineAccess aPa, final @NotNull ICompilationAccess ca0) {
		pa = aPa;

		// TODO annotation time, or use clj
		pa.install_notate(Provenance.PipelineLogic__nextModule, GN_PL_Run2.class, GN_PL_Run2_Env.class);

		this.ca = ca0;
		ca.setPipelineLogic(this);
		generatePhase = new GeneratePhase(ca.testSilence(), pa, this);
		dp            = new DeducePhase(ca, pa, this);

		pa.getCompilationEnclosure().addModuleListener(new PL_ModuleListener(this, pa));
	}

	public Verbosity getVerbosity() {
		// 24/01/04 back and forth
		return ca.testSilence();
	}

	public interface GDM_Pipeline {
		GDM_Product submit(final Object aMod);
	}

	public interface GDM_Product {
		static GDM_Product of(Object aGDPModuleCompletableProcess) {
			return new GDM_Product() {
				@Override
				public List<EvaNode> getNodes() {
					if (aGDPModuleCompletableProcess instanceof GDP_ModuleCompletableProcess gdp)
						return gdp.getNodes();
					else return Helpers0.List_of();
				}

				@Override
				public CompletableProcess<GDP_Module> getProcess() {
					if (aGDPModuleCompletableProcess instanceof GDP_ModuleCompletableProcess gdp)
						return gdp;
					else return null;
				}
			};
		}

		List<EvaNode> getNodes();
		CompletableProcess<GDP_Module> getProcess();
	}

	public ModuleCompletableProcess _mcp() {
		return mcp;
	}

	public @NonNull IPipelineAccess _pa() {
		return pa;
	}

	public void addLog(ElLog aLog) {
		_pa().addLog(aLog);
	}

	@Override
	public void checkFinishEventuals() {
		int y = 0;
		for (Eventual<?> eventual : _eventuals) {
			if (eventual.isResolved()) {
			} else {
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[PipelineLogic::checkEventual] failed for " + eventual.description());
			}
		}
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

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}

	interface GDP_Module {
		OS_Module getSource();

		// TODO 24/01/22  need EvaModule?

		//promise??
		List<EvaNode> getGeneratedProducts();

		List<EvaNode> getDeducedProducts();
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

	private static class GDP_ModuleCompletableProcess implements CompletableProcess<GDP_Module> {
		private final List<EvaNode> nodes; // you won!!

		private GDP_ModuleCompletableProcess(List<EvaNode> aNodes) {
			nodes = aNodes;
		}

		@Override
		public void add(final GDP_Module item) {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void complete() {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void error(final Diagnostic d) {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void preComplete() {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		@Override
		public void start() {
			throw new UnintendedUseException("not expected, remove after tests");
		}

		public List<EvaNode> getNodes() {
			return this.nodes;
		}
	}
}

//
//
//
