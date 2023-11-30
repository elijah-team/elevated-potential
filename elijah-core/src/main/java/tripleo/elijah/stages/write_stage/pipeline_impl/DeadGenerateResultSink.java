package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.nextgen.GEvaClass;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingNamespace;
import tripleo.util.buffer.Buffer;

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
	public void addClass_0(final GarishClass aGarishClass, final Buffer aImplBuffer, final Buffer aHeaderBuffer) {
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
	public void addNamespace_0(final GarishNamespace aLivingNamespace, final Buffer aImplBuffer,
							   final Buffer aHeaderBuffer) {
		throw new UnintendedUseException();
	}

	@Override
	public void addNamespace_1(final GarishNamespace aGarishNamespace, final GenerateResult aGenerateResult,
							   final GenerateC aGenerateC) {
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable LivingClass getLivingClassForEva(final GEvaClass aEvaClass) {
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable LivingNamespace getLivingNamespaceForEva(final EvaNamespace aEvaClass) {
		throw new UnintendedUseException();
	}
}
