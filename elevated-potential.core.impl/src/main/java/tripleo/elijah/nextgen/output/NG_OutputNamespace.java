package tripleo.elijah.nextgen.output;

import static tripleo.elijah.util.Helpers.*;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.util.*;

public class NG_OutputNamespace implements NG_OutputItem {
	private GarishNamespace garishNamespace;
	private GenerateC generateC;

	@Override
	public @NotNull List<NG_OutputStatement> getOutputs() {
		final EvaNamespace x = garishNamespace.getLiving().evaNode();
		var m = x.module();

		final String class_name;

		if (x.getCode() != 0) {
			class_name = "ZN%d".formatted(x.getCode());
		} else {
			class_name = x.getName();
		}

		final BufferTabbedOutputStream tos = garishNamespace.getImplBuffer(x, class_name, x.getCode());
		var implText = new NG_OutputNamespaceStatement(tos, m, GenerateResult.TY.IMPL);

		final BufferTabbedOutputStream tosHdr = garishNamespace.getHeaderBuffer(generateC, x, class_name, x.getCode());
		var headerText = new NG_OutputNamespaceStatement(tosHdr, m, GenerateResult.TY.HEADER);

		return List_of(implText, headerText);
	}

	@Override
	public EOT_FileNameProvider outName(final @NotNull OutputStrategyC aOutputStrategyC,
			final GenerateResult.@NotNull TY ty) {
		final EvaNamespace x = garishNamespace.getLiving().evaNode();

		return aOutputStrategyC.nameForNamespace1(x, ty);
	}

	public void setNamespace(final GarishNamespace aGarishNamespace, final GenerateC aGenerateC) {
		garishNamespace = aGarishNamespace;
		generateC = aGenerateC;
	}
}
