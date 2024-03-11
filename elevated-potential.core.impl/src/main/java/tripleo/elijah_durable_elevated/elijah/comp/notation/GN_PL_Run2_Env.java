package tripleo.elijah_durable_elevated.elijah.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.util.Objects;
import java.util.function.Consumer;

public final class GN_PL_Run2_Env implements GN_Env {
	private final @NotNull PipelineLogic         pipelineLogic;
	private final @NotNull WorldModule           mod;
	private final @NotNull CompilationEnclosure  ce;
	private final @NotNull Consumer<WorldModule> worldConsumer;

	public GN_PL_Run2_Env(@NotNull PipelineLogic pipelineLogic, @NotNull WorldModule mod,
						  @NotNull CompilationEnclosure ce, @NotNull Consumer<WorldModule> worldConsumer) {
		this.pipelineLogic = pipelineLogic;
		this.mod           = mod;
		this.ce            = ce;
		this.worldConsumer = worldConsumer;
	}

	public @NotNull PipelineLogic pipelineLogic() {
		return pipelineLogic;
	}

	public @NotNull WorldModule mod() {
		return mod;
	}

	public @NotNull CompilationEnclosure ce() {
		return ce;
	}

	public @NotNull Consumer<WorldModule> worldConsumer() {
		return worldConsumer;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (GN_PL_Run2_Env) obj;
		return Objects.equals(this.pipelineLogic, that.pipelineLogic) &&
				Objects.equals(this.mod, that.mod) &&
				Objects.equals(this.ce, that.ce) &&
				Objects.equals(this.worldConsumer, that.worldConsumer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pipelineLogic, mod, ce, worldConsumer);
	}

	@Override
	public String toString() {
		return "GN_PL_Run2_Env[" +
				"pipelineLogic=" + pipelineLogic + ", " +
				"mod=" + mod + ", " +
				"ce=" + ce + ", " +
				"worldConsumer=" + worldConsumer + ']';
	}

}
