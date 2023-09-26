package tripleo.elijah.stages.write_stage.pipeline_impl;

import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.nextgen.output.NG_OutputStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;

// TODO 09/04 Duplication madness
public record NG_OutputRequest(
		EOT_OutputFile.FileNameProvider fileName,
		EG_Statement statement,
		NG_OutputStatement outputStatement,
		NG_OutputItem outputItem
) {
}
