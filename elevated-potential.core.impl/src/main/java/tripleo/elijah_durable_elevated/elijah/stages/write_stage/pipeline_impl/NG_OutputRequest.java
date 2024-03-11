package tripleo.elijah_durable_elevated.elijah.stages.write_stage.pipeline_impl;

import tripleo.elijah_durable_elevated.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah_durable_elevated.elijah.nextgen.output.NG_OutputStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.*;

import java.util.Objects;

// TODO 09/04 Duplication madness
public final class NG_OutputRequest {
	private final EOT_FileNameProvider fileName;
	private final EG_Statement         statement;
	private final NG_OutputStatement   outputStatement;
	private final NG_OutputItem        outputItem;

	public NG_OutputRequest(
			EOT_FileNameProvider fileName,
			EG_Statement statement,
			NG_OutputStatement outputStatement,
			NG_OutputItem outputItem
						   ) {
		this.fileName        = fileName;
		this.statement       = statement;
		this.outputStatement = outputStatement;
		this.outputItem      = outputItem;
	}

	public EOT_FileNameProvider fileName() {
		return fileName;
	}

	public EG_Statement statement() {
		return statement;
	}

	public NG_OutputStatement outputStatement() {
		return outputStatement;
	}

	public NG_OutputItem outputItem() {
		return outputItem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, statement, outputStatement, outputItem);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (NG_OutputRequest) obj;
		return Objects.equals(this.fileName, that.fileName) &&
				Objects.equals(this.statement, that.statement) &&
				Objects.equals(this.outputStatement, that.outputStatement) &&
				Objects.equals(this.outputItem, that.outputItem);
	}

	@Override
	public String toString() {
		return "NG_OutputRequest[" +
				"fileName=" + fileName + ", " +
				"statement=" + statement + ", " +
				"outputStatement=" + outputStatement + ", " +
				"outputItem=" + outputItem + ']';
	}

}
