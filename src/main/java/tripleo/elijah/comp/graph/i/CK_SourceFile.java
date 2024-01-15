package tripleo.elijah.comp.graph.i;

import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;

import java.util.function.Consumer;

public interface CK_SourceFile<T> {
	CompilerInput compilerInput();

	EIT_Input input(); // s ??

	EOT_OutputFile output(); // s ??

	Operation2<T> process_query();

	void associate(CompilationClosure aCc);

	void associate(CompilerInput aInput, CompilationClosure aCc);

	void process_query2(CompilationClosure cc, Consumer<Operation<T>> cb);
}
