package tripleo.elijah.comp;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.vendor.mal.stepA_mal;

import java.util.List;

public class AccessBus {
	public interface AB_GenerateResultListener {
		void gr_slot(GenerateResult gr);
	}

	public interface AB_LgcListener {
		void lgc_slot(List<EvaNode> lgc);
	}

	public interface AB_PipelineLogicListener {
		void pl_slot(PipelineLogic lgc);
	}

	public final  Old_GenerateResult gr = new Old_GenerateResult();
	private final Compilation       _c;
	private final IPipelineAccess    _pa;
	private final stepA_mal.@NotNull MalEnv2 env;
	private final DeferredObject<GenerateResult, Void, Void> generateResultPromise = new DeferredObject<>();

	private final DeferredObject<List<EvaNode>, Void, Void> lgcPromise = new DeferredObject<>();

	public AccessBus(final Compilation aC, final IPipelineAccess aPa) {
		_c = aC;
		_pa = aPa;

		env = new stepA_mal.MalEnv2(null); // TODO what does null mean?
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

	public void subscribe_GenerateResult(@NotNull final AB_GenerateResultListener aGenerateResultListener) {
		generateResultPromise.then(aGenerateResultListener::gr_slot);
	}

	public void subscribe_lgc(@NotNull final AB_LgcListener aLgcListener) {
		lgcPromise.then(aLgcListener::lgc_slot);
	}

	public void subscribePipelineLogic(final DoneCallback<PipelineLogic> aPipelineLogicDoneCallback) {
		_pa.getPipelineLogicPromise().then(aPipelineLogicDoneCallback);
	}
}
