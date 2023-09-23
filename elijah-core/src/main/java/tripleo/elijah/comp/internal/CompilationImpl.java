/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.__Compilation1;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.stages.deduce.IFunctionMapHook;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah.stages.deduce.fluffy.impl.FluffyCompImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CompilationImpl extends __Compilation1 {

	private final @NotNull FluffyCompImpl _fluffyComp;
	private @Nullable      EOT_OutputTree _output_tree = null;

	public CompilationImpl(final ErrSink aEee, final IO aIo) {
		super(aEee, aIo);
		_fluffyComp = new FluffyCompImpl(this);
	}

	public @NotNull ICompilationAccess _access() {
		return new DefaultCompilationAccess(this);
	}

	//@Override
	//public void fakeFlow(final List<CompilerInput> aInputs, final @NotNull CompilationFlow aFlow) {
	//	getCompilationEnclosure().getPipelineAccessPromise()
	//			.then(pa -> {
	//				get_pa().setCompilerInput(aInputs);
	//
	//				aFlow.run(this);
	//			});
	//}

	@Override
	public @NotNull FluffyComp getFluffy() {
		return _fluffyComp;
	}

	@Override
	public @NotNull EOT_OutputTree getOutputTree() {
		if (_output_tree == null) {
			_output_tree = new EOT_OutputTree();
		}

		assert _output_tree != null;

		return _output_tree;
	}

	@Deprecated
//	@Override
	public List<OS_Module> modules() {
		return this.world().modules()
				.stream()
				.map(wm -> wm.module())
				.collect(Collectors.toList());
	}

	public CompilerBeginning beginning(final @NotNull CompilationRunner compilationRunner) {
		return new CompilerBeginning(this, getRootCI(), getInputs(), compilationRunner.getProgressSink(), cfg());
	}

	@Override
	public CompilerInputListener getCompilerInputListener() {
		return cci_listener;
	}

	public void testMapHooks(final List<IFunctionMapHook> aMapHooks) {
		//pipelineLogic.dp.
	}

	@Override
	public Map<String, CompilerInstructions> fn2ci() {
		return fn2ci;
	}

	@Override
	public USE use() {
		return getUse();
	}

	@Override
	public CIS _cis() {
		return get_cis();
	}
}

//
//
//
