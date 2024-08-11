/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.stages.gen_generic;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.util.buffer.*;

import java.util.*;
import java.util.stream.*;

/**
 * Created 4/27/21 1:12 AM
 */
public class Old_GenerateResultItem implements GenerateResultItem {
	public final @NotNull Buffer buffer;
	public final int counter;
	public final @NotNull LibraryStatementPart lsp;
	public final @NotNull EvaNode node;
	public final @NotNull Old_GenerateResult.TY ty;
	private final @NotNull Dependency dependency;
	public String output;
	//public IOutputFile outputFile;

	@Contract(pure = true)
	public Old_GenerateResultItem(final @NotNull Old_GenerateResult.TY aTy,
								  final @NotNull Buffer aBuffer,
								  final @NotNull EvaNode aNode,
								  final @NotNull LibraryStatementPart aLsp,
								  final @NotNull Dependency aDependency,
								  final int aCounter) {
		ty = aTy;
		buffer = aBuffer;
		node = aNode;
		lsp = aLsp;
		dependency = aDependency;
		counter = aCounter;
	}

	@Override
	public GenerateResult.@NotNull TY __ty() {
		return ty;
	}

	@Override
	public int _counter() {
		return counter;
	}

	@Override
	public @NotNull Buffer buffer() {
		return this.buffer;
	}

	@Override
	public @NotNull List<DependencyRef> dependencies() {
		List<DependencyRef> x = dependency.getNotedDeps().stream().map(dep -> dep.dref).collect(Collectors.toList());
		return x;
	}

	@Override
	public @NotNull Dependency getDependency() {
		final List<DependencyRef> ds = dependencies();
		return dependency;
	}

	@Override
	public @NotNull String jsonString() {
		final String sb = "{\".class\": \"GenerateResultItem\", " + "counter: " + counter + ", " + "ty: " + ty + ", " + "output: " + output + ", "
				//+ "outputFile: " + outputFile
				+ ", " + "lsp: " + lsp + ", " + "node: " + node + ", " + "buffer: \"\"\"" + buffer.getText() + "\"\"\", " + "dependency: " + dependency.jsonString() + ", " + "dependencies: " + dependencies() + /* +", " */
				"}";
		return sb;
	}

	@Override
	public @NotNull LibraryStatementPart lsp() {
		return this.lsp;
	}

	@Override
	public @NotNull EvaNode node() {
		return this.node;
	}

	@Override
	public String output() {
		return this.output;
	}

	@Override
	public @NotNull String toString() {
		return "GenerateResultItem{" + "counter=" + counter + ", ty=" + ty + ", buffer=" + buffer.getText() + ", node="
				+ node.identityString() + ", lsp=" + lsp.getDirName() + ", dependency=" + dependency.jsonString()
				+ ", output='" + output + '\''
				//+ ", outputFile=" + outputFile
				+ '}';
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
