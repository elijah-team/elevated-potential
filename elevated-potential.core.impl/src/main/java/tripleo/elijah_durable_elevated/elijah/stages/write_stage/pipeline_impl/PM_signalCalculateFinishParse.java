package tripleo.elijah_durable_elevated.elijah.stages.write_stage.pipeline_impl;

import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;

import java.util.List;

public class PM_signalCalculateFinishParse {
	private final GenerateResult                   result;
	private final List<EvaClass>                   cs;
	private final List<EvaNamespace>               ns;
	private final List<BaseEvaFunction>            fs;
	private final WPIS_GenerateOutputs.OutputItems itms;

	public GenerateResult getResult() {
		return result;
	}

	public List<EvaClass> getCs() {
		return cs;
	}

	public List<EvaNamespace> getNs() {
		return ns;
	}

	public List<BaseEvaFunction> getFs() {
		return fs;
	}

	public WPIS_GenerateOutputs.OutputItems getItms() {
		return itms;
	}

	public PM_signalCalculateFinishParse(final GenerateResult aResult, final List<EvaClass> aCs, final List<EvaNamespace> aNs, final List<BaseEvaFunction> aFs, final WPIS_GenerateOutputs.OutputItems aItms) {
		result = aResult;
		cs     = aCs;
		ns     = aNs;
		fs     = aFs;
		itms   = aItms;
	}
}
