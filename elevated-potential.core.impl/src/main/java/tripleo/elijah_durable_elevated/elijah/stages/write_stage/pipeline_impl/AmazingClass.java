package tripleo.elijah_durable_elevated.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.nextgen.output.NG_OutputClass;
import tripleo.elijah_durable_elevated.elijah.stages.garish.GarishClass;
import tripleo.elijah_durable_elevated.elijah.stages.gen_c.GenerateC;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaClass;

class AmazingClass implements Amazing {
	private final OS_Module                        mod;
	private final Compilation                      compilation;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final EvaClass                         c;

	public AmazingClass(final @NotNull EvaClass c,
	                    final @NotNull WPIS_GenerateOutputs.OutputItems aOutputItems,
	                    final IPipelineAccess aPa) {
		this.c      = c;
		mod         = c.module();
		compilation = (Compilation) mod.getCompilation();
		itms        = aOutputItems;
	}

	public OS_Module mod() {
		return mod;
	}

	void waitGenC(final GenerateC ggc) {
		var oc = new NG_OutputClass();
		oc.setClass((GarishClass) compilation.world().getClass(c).getGarish(), ggc);
		itms.addItem(oc);
	}
}
