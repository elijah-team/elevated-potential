package tripleo.elijah.comp.graph.i;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;

public interface CK_SourceFile<T> {
	CompilerInput compilerInput();

	EIT_Input input(); // s ??

	EOT_OutputFile output(); // s ??

	Operation2<T> process_query();

	void associate(CompilationClosure aCc);

	void associate(CompilerInput aInput, CompilationClosure aCc);
}
