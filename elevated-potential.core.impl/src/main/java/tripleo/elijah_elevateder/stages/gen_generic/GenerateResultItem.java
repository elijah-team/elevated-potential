package tripleo.elijah_elevateder.stages.gen_generic;

import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNode;
import tripleo.util.buffer.Buffer;

import java.util.List;

public interface GenerateResultItem {

	GenerateResult.TY __ty();

	int _counter();

	Buffer buffer();

	List<DependencyRef> dependencies();

	Dependency getDependency();

	String jsonString();

	LibraryStatementPart lsp();

	EvaNode node();

	String output();

	@Override
	String toString();
}