package tripleo.elijah.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.ElijahInternal;
import tripleo.elijah.util.UnintendedUseException;

import tripleo.elijah.stages.gen_generic.GRS_Addable;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;

import tripleo.elijah.comp.i.extra.IPipelineAccess;

import tripleo.elijah.nextgen.output.NG_OutputFunction;
import tripleo.elijah.stages.gen_c.C2C_Result;

import tripleo.elijah.g.GEvaClass;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.world.i.LivingClass;

import tripleo.elijah.g.GEvaNamespace;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.world.i.LivingNamespace;

import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;

import java.util.List;

public class DefaultGenerateResultSink implements GenerateResultSink {
	private final @NotNull IPipelineAccess pa;

	@Contract(pure = true)
	public DefaultGenerateResultSink(final @NotNull IPipelineAccess pa0) {
		pa = pa0;
	}

	@Override
	public void add(final GRS_Addable aAddable) {
		assert aAddable instanceof EvaNode;

		if (aAddable instanceof EvaNode) {
			// README 11/30 Preserve original behavior by throwing
			//throw new IllegalStateException("Error");
			throw new UnintendedUseException();
		} else {
			aAddable.action(this);
		}
	}

	@Override
	public void addFunction(final BaseEvaFunction aGf, final List<C2C_Result> aRs, final GenerateFiles aGenerateFiles) {
		NG_OutputFunction o = new NG_OutputFunction();
		o.setFunction(aGf, aGenerateFiles, aRs);
		pa.addOutput(o);
	}

	@Override
	public void additional(final @NotNull GenerateResult aGenerateResult) {
		// throw new IllegalStateException("Error");
	}

	@Override
	public LivingClass getLivingClassForEva(final GEvaClass aEvaClass) {
		return pa.getCompilation().world().getClass((EvaClass) aEvaClass);
	}

	@Override
	public LivingNamespace getLivingNamespaceForEva(final GEvaNamespace aEvaNamespace) {
		return pa.getCompilation().world().getNamespace((EvaNamespace) aEvaNamespace);
	}

	@ElijahInternal
	@Override
	public IPipelineAccess pa() {
		return pa;
	}
}
