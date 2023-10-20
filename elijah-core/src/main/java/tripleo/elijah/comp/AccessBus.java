package tripleo.elijah.comp;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal.CR_State.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.vendor.mal.*;

import java.util.*;
import java.util.function.*;

public class AccessBus {
	public interface AB_GenerateResultListener {
		void gr_slot(GenerateResult gr);
	}

	public interface AB_LgcListener {
		void lgc_slot(List<EvaNode> lgc);
	}

	public final  Old_GenerateResult gr = new Old_GenerateResult();
	private final Compilation       _c;
	private final IPipelineAccess    _pa;
	private final stepA_mal.@NotNull MalEnv2 env;
	private final DeferredObject<GenerateResult, Void, Void> generateResultPromise = new DeferredObject<>();

	private final DeferredObject<List<EvaNode>, Void, Void> lgcPromise = new DeferredObject<>();

	private final Map<String, CR_State.PipelinePlugin> pipelinePlugins = new HashMap<>();

	public AccessBus(final Compilation aC, final IPipelineAccess aPa) {
		_c = aC;
		_pa = aPa;

		env = new stepA_mal.MalEnv2(null); // TODO what does null mean?
	}

	public void add(final @NotNull Function<AccessBus, PipelineMember> aCr) {
		final PipelineMember x = aCr.apply(this);
		_c.getCompilationEnclosure().getCompilationAccess().addPipeline(x);
	}

	public void addPipelinePlugin(final @NotNull PipelinePlugin aPlugin) {
		pipelinePlugins.put(aPlugin.name(), aPlugin);
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

	public @Nullable PipelinePlugin getPipelinePlugin(final String aPipelineName) {
		if (!(pipelinePlugins.containsKey(aPipelineName)))
			return null;

		return pipelinePlugins.get(aPipelineName);
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
