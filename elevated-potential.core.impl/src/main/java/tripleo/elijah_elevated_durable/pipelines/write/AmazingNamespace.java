package tripleo.elijah_elevated_durable.pipelines.write;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.nextgen.output.NG_OutputNamespace;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNamespace;

class AmazingNamespace implements Amazing {
	private final OS_Module                        mod;
	private final Compilation                     compilation;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final EvaNamespace n;

	public AmazingNamespace(final @NotNull EvaNamespace n, final WPIS_GenerateOutputs.OutputItems itms,
			final IPipelineAccess aPa) {
		this.n = n;
		mod = n.module();
		compilation = (Compilation) mod.getCompilation();
		this.itms = itms;
	}

	public OS_Module mod() {
		return mod;
	}

	void waitGenC(final GenerateC ggc) {
		var on = new NG_OutputNamespace();
		on.setNamespace(compilation.world().getNamespace(n).getGarish(), ggc);
		itms.addItem(on);
	}
}
