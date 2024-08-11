package tripleo.elijah.stages.gen_generic;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.util.buffer.*;

import java.util.*;

public class New_GenerateResultItem implements GenerateResultItem {
	private final Old_GenerateResultItem ogri;

	public New_GenerateResultItem(final GenerateResult.@NotNull TY aTy,
								  final @NotNull Buffer aBuffer,
								  final @NotNull EvaNode aNode,
								  final @NotNull LibraryStatementPart aLsp,
								  final @NotNull Dependency aDependency,
								  final int aCounter) {
		ogri = new Old_GenerateResultItem(aTy, aBuffer, aNode, aLsp, aDependency, aCounter);
	}

	@Override
	public GenerateResult.@NotNull TY __ty() {
		return ogri.__ty();
	}

	@Override
	public int _counter() {
		return ogri._counter();
	}

	@Override
	@NotNull
	public Buffer buffer() {
		return ogri.buffer();
	}

	@Override
	@NotNull
	public List<DependencyRef> dependencies() {
		return ogri.dependencies();
	}

	@Override
	@NotNull
	public Dependency getDependency() {
		return ogri.getDependency();
	}

	@Override
	@NotNull
	public String jsonString() {
		return ogri.jsonString();
	}

	@Override
	@NotNull
	public LibraryStatementPart lsp() {
		return ogri.lsp();
	}

	@Override
	@NotNull
	public EvaNode node() {
		return ogri.node();
	}

	@Override
	public String output() {
		return ogri.output();
	}

	@Override
	@NotNull
	public String toString() {
		return ogri.toString();
	}
}
