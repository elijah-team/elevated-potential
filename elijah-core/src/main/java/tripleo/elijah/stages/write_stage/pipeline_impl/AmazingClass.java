package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;

class AmazingClass implements Amazing {
	private final OS_Module                        mod;
	private final Compilation                     compilation;
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
		oc.setClass(compilation.world().getClass(c).getGarish(), ggc);
		itms.addItem(oc);
	}
}
