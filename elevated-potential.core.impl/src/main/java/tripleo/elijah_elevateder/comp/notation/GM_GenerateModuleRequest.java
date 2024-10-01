package tripleo.elijah_elevateder.comp.notation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateFiles;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_elevateder.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah_elevateder.world.i.WorldModule;

import java.util.Objects;
import java.util.function.Supplier;

public final class GM_GenerateModuleRequest implements GN_Env {
	private final @NotNull GN_GenerateNodesIntoSink    generateNodesIntoSink;
	private final @NotNull WorldModule                 mod;
	private final @NotNull GN_GenerateNodesIntoSinkEnv env;

	public GM_GenerateModuleRequest(@NotNull GN_GenerateNodesIntoSink generateNodesIntoSink,
									@NotNull WorldModule mod, @NotNull GN_GenerateNodesIntoSinkEnv env) {
		this.generateNodesIntoSink = generateNodesIntoSink;
		this.mod                   = mod;
		this.env                   = env;
	}

	@Contract("_ -> new")
	public @NotNull GenerateFiles getGenerateFiles(final Supplier<GenerateResultEnv> fgs) {
		var params = params();
		return env.getGenerateFiles(params, params.getWorldMod(), fgs);
	}

	public @NotNull OutputFileFactoryParams params() {
		return env.getParams(mod, generateNodesIntoSink);
	}

	public @NotNull GN_GenerateNodesIntoSink generateNodesIntoSink() {
		return generateNodesIntoSink;
	}

	public @NotNull WorldModule mod() {
		return mod;
	}

	public @NotNull GN_GenerateNodesIntoSinkEnv env() {
		return env;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (GM_GenerateModuleRequest) obj;
		return Objects.equals(this.generateNodesIntoSink, that.generateNodesIntoSink) &&
				Objects.equals(this.mod, that.mod) &&
				Objects.equals(this.env, that.env);
	}

	@Override
	public int hashCode() {
		return Objects.hash(generateNodesIntoSink, mod, env);
	}

	@Override
	public String toString() {
		return "GM_GenerateModuleRequest[" +
				"generateNodesIntoSink=" + generateNodesIntoSink + ", " +
				"mod=" + mod + ", " +
				"env=" + env + ']';
	}

}
