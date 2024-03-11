package tripleo.elijah_durable_elevated.elijah.stages.gen_fn_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah_durable_elevated.elijah.comp.FunctionStatement;
import tripleo.elijah_durable_elevated.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.entrypoints.ArbitraryFunctionEntryPoint;
import tripleo.elijah_durable_elevated.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah_durable_elevated.elijah.pre_world.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.inter.ModuleThing;

public class GenFnC {
	private IPipelineAccess pa;
	private PipelineLogic   pipelineLogic;

	public void set(final IPipelineAccess aPa0) {
		this.pa = aPa0;
	}

	public void set(final PipelineLogic aPl) {
		pipelineLogic = aPl;
	}

	public void addLog(final ElLog aLOG) {
		pa.addLog(aLOG);
	}

	public void addEntryPoint(final Mirror_EntryPoint aMirrorEntryPoint, final IClassGenerator aDcg) {
		pa.getCompilationEnclosure().addEntryPoint(aMirrorEntryPoint, aDcg);
	}

	public void addEntryPoint(final EntryPoint aEntryPoint,
							  final ModuleThing aMt,
							  final IClassGenerator aDcg,
							  final GenerateFunctions gf) {
		addEntryPoint(getMirrorEntryPoint(aEntryPoint, aMt, gf), aDcg);
	}

	@NotNull
	private Mirror_EntryPoint getMirrorEntryPoint(final EntryPoint entryPoint, final ModuleThing mt, final GenerateFunctions gf) {
		final Mirror_EntryPoint m;
		if (entryPoint instanceof final @NotNull MainClassEntryPoint mcep) {
			m = new Mirror_MainClassEntryPoint(mcep, mt, gf);
		} else if (entryPoint instanceof final @NotNull ArbitraryFunctionEntryPoint afep) {
			m = new Mirror_ArbitraryFunctionEntryPoint(afep, mt, gf);
		} else {
			throw new IllegalStateException("unhandled");
		}
		return m;
	}

	public GeneratePhase getGeneratePhase() {
		return pipelineLogic.generatePhase;
	}

	public void addFunctionStatement(final EvaFunction aGf) {
		pa.addFunctionStatement(new FunctionStatement(aGf));
	}
}
