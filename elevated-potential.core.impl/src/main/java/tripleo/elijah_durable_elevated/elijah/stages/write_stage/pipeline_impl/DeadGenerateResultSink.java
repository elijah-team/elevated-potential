package tripleo.elijah_durable_elevated.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_durable_elevated.elijah.world.i.LivingClass;
import tripleo.elijah_durable_elevated.elijah.world.i.LivingNamespace;

import java.util.List;

/**
 * All methods implemented here should throw {@code UnintendedUseException}
 */
public abstract class DeadGenerateResultSink implements GenerateResultSink {
	@Override
	public void add(final GRS_Addable node) {
		throw new UnintendedUseException();
	}

	@Override
	public void addFunction(final BaseEvaFunction aGf, final List<C2C_Result> aRs, final GenerateFiles aGenerateFiles) {
		throw new UnintendedUseException();
	}

	@Override
	public void additional(final GenerateResult aGenerateResult) {
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable LivingClass getLivingClassForEva(final GEvaClass aEvaClass) {
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable LivingNamespace getLivingNamespaceForEva(final GEvaNamespace aEvaClass) {
		throw new UnintendedUseException();
	}

	@Override
	public GPipelineAccess pa() {
		throw new UnintendedUseException();
	}
}
