package tripleo.elijah_elevateder.stages.gen_c;

import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah_elevateder.stages.gen_fn.BaseTableEntry;
import tripleo.elijah_elevateder.stages.instructions.InstructionArgument;
import tripleo.elijah_fluffy.util.Operation2;

public interface CR_ReferenceItem {
	String getArg();

	CReference.Connector getConnector();

	GenerateC_Item getGenerateCItem();

	InstructionArgument getInstructionArgument();

	Eventual<GenerateC_Item> getPrevious();

	CReference.Reference getReference();

	Operation2<EG_Statement> getStatement();

	BaseTableEntry getTableEntry();

	String getText();

	void setArg(String aArg);

	void setConnector(CReference.Connector aConnector);

	void setGenerateCItem(GenerateC_Item aGenerateCItem);

	void setInstructionArgument(InstructionArgument aInstructionArgument);

	void setPrevious(Eventual<GenerateC_Item> aPrevious);

	void setReference(CReference.Reference aReference);

	void setStatement(Operation2<EG_Statement> aStatement);

	void setText(String aText);
}
