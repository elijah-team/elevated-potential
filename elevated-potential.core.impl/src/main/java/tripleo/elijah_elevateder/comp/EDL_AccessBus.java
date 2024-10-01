package tripleo.elijah_elevateder.comp;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNode;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.Old_GenerateResult;
import tripleo.vendor.mal.stepA_mal;

import java.util.List;

public class EDL_AccessBus {
	public interface AB_GenerateResultListener {
		void gr_slot(GenerateResult gr);
	}

	public interface AB_LgcListener {
		void lgc_slot(List<EvaNode> lgc);
	}

	public interface AB_PipelineLogicListener {
		void pl_slot(PipelineLogic lgc);
	}

	public  final Old_GenerateResult gr = new Old_GenerateResult();
	private final Compilation        _c;
	private final IPipelineAccess    _pa;
	private final @NotNull stepA_mal.MalEnv2 env;

	private final Eventual<GenerateResult> generateResultPromise = new Eventual<>();
	private final Eventual<List<EvaNode>> lgcPromise 		 = new Eventual<>();

	public EDL_AccessBus(final Compilation aC, final IPipelineAccess aPa) {
		_c = aC;
		_pa = aPa;

		env = new stepA_mal.MalEnv2(null); // TODO what does null mean?

		generateResultPromise.register((EDL_Compilation)_c);
		lgcPromise           .register((EDL_Compilation)_c);
	}

	public stepA_mal.@NotNull MalEnv2 env() {
		return env;
	}

	public @NotNull Compilation getCompilation() {
		return _c;
	}

	public IPipelineAccess getPipelineAccess() {
		return _pa;
	}

	public void resolveGenerateResult(final GenerateResult aGenerateResult) {
		generateResultPromise.resolve(aGenerateResult);
	}

	public void resolvePipelineLogic(final PipelineLogic aPipelineLogic) {
		_pa.getPipelineLogicPromise().resolve(aPipelineLogic);
	}

	public void subscribe_GenerateResult(final @NotNull AB_GenerateResultListener aGenerateResultListener) {
		generateResultPromise.then(aGenerateResultListener::gr_slot);
	}

	public void subscribe_lgc(final @NotNull AB_LgcListener aLgcListener) {
		lgcPromise.then(aLgcListener::lgc_slot);
	}

	public void subscribePipelineLogic(final DoneCallback<PipelineLogic> aPipelineLogicDoneCallback) {
		_pa.getPipelineLogicPromise().then(aPipelineLogicDoneCallback);
	}
}
