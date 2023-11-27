package tripleo.elijah.nextgen.outputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;

import java.util.*;

public interface EOT_OutputFile {
	String getFilename();

	@NotNull List<EIT_Input> getInputs();

	EG_Statement getStatementSequence();

	EOT_OutputType getType();

	@Override
	String toString();

	//@Override
	//default String toString() {
	//	return "(%s) '%s'".formatted(_type, _filename.getFilename());
	//}
}
