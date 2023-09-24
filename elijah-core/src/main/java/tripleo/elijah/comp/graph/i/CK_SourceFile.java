package tripleo.elijah.comp.graph.i;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;

public interface CK_SourceFile {
	CompilerInput compilerInput();

	EIT_Input input(); // s ??

	EOT_OutputFile output(); // s ??

	Operation<CompilerInstructions> process_query();
}