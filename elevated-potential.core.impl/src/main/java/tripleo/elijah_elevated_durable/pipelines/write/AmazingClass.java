package tripleo.elijah_elevated_durable.pipelines.write;

import org.jetbrains.annotations.*;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.garish.GarishClass;

import tripleo.elijah_elevateder.nextgen.output.NG_OutputClass;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;

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
