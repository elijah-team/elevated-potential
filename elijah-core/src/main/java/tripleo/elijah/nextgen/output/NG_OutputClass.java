package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.util.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class NG_OutputClass implements NG_OutputItem {
	private GarishClass   garishClass;
	private GenerateFiles generateFiles;

	@Override
	public @NotNull List<NG_OutputStatement> getOutputs() {
		final EvaClass x = garishClass.getLiving().evaNode();

		var generateC = (GenerateC) generateFiles;

		final BufferTabbedOutputStream tos      = garishClass.getImplBuffer(generateC);
		var                            implText = new NG_OutputClassStatement(tos, x.module(), GenerateResult.TY.IMPL);

		final BufferTabbedOutputStream tosHdr     = garishClass.getHeaderBuffer(generateC);
		var                            headerText = new NG_OutputClassStatement(tosHdr, x.module(), GenerateResult.TY.HEADER);

		return List_of(implText, headerText);
	}

	@Override
	public EOT_OutputFile.FileNameProvider outName(final @NotNull OutputStrategyC aOutputStrategyC,
	                                               final GenerateResult.@NotNull TY ty) {
		final EvaClass x = garishClass.getLiving().evaNode();

		return aOutputStrategyC.nameForClass1(x, ty);
	}

	public void setClass(final GarishClass aGarishClass, final GenerateFiles aGenerateFiles) {
		garishClass   = aGarishClass;
		generateFiles = aGenerateFiles;
	}
}
