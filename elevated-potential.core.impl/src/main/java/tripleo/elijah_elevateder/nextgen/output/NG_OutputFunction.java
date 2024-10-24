package tripleo.elijah_elevateder.nextgen.output;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;

import tripleo.elijah_elevateder.stages.gen_c.C2C_Result;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateFiles;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.generate.OutputStrategyC;

import java.util.*;

public class NG_OutputFunction implements NG_OutputItem {
	private List<C2C_Result> collect;
	private GenerateFiles    generateFiles;
	private BaseEvaFunction gf;

	@Override
	public @NotNull List<NG_OutputStatement> getOutputs() {
		final List<NG_OutputStatement> r = new ArrayList<>();

		if (collect != null) {
			for (C2C_Result c2c : collect) {
				final EG_Statement      x = c2c.getStatement();
				final GenerateResult.TY y = c2c.ty();

				r.add(new NG_OutputFunctionStatement(c2c));
			}
		}

		return r;
	}

	@Override
	public EOT_FileNameProvider outName(final @NotNull OutputStrategyC aOutputStrategyC,
										final GenerateResult.@NotNull TY ty) {
		if (gf instanceof EvaFunction) {
			return aOutputStrategyC.nameForFunction1((EvaFunction) gf, ty);
		} else {
			return aOutputStrategyC.nameForConstructor1((IEvaConstructor) gf, ty);
		}
	}

	public void setFunction(final BaseEvaFunction aGf, final GenerateFiles aGenerateFiles,
			final List<C2C_Result> aCollect) {
		gf = aGf;
		generateFiles = aGenerateFiles;
		collect = aCollect;
	}
}
