package tripleo.elijah.comp.internal;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.graph.i.CK_SourceFile;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.comp.nextgen.i.Asseveration;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;

public class DefaultObjectTree implements CK_ObjectTree {
	private final CompilationImpl compilation;
	private       EIT_ModuleList  moduleList;

	public DefaultObjectTree(final CompilationImpl aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public void asseverate(Object o, Asseverate asseveration) {
		switch (asseveration) {
		case ELIJAH_PARSED -> {
/*
			var x = (Pair<ElijahSpec, Operation<OS_Module>>)o;

			var spec = x.getLeft();
			var calm = x.getRight();

			var pl = getCompilationEnclosure().getPipelineLogic();

			var wm = new DefaultWorldModule(calm.success(), getCompilationEnclosure());
			System.err.println("**************************************************Comp ELIJAH_PARSED  "+wm.module().getFileName());
//				pl.addModule(wm);
*/
		}
		case CI_HASHED -> {
			Triple<EzSpec, CK_SourceFile, Operation<String>> t = (Triple<EzSpec, CK_SourceFile, Operation<String>>) o;

			var spec = t.getLeft();
			var hash = t.getRight();
			var p    = t.getMiddle();

			compilation.getCompilationEnclosure().logProgress(CompProgress.Ez__HasHash, Pair.of(spec, hash.success()));

			if (p.compilerInput() != null) {
				p.compilerInput().accept_hash(hash.success());
			} else {
				NotImplementedException.raise_stop();
			}
		}
		}
//			NotImplementedException.raise_stop();
	}

	@Override
	public void asseverate(final Asseveration aAsseveration) {
		aAsseveration.onLogProgress(compilation.getCompilationEnclosure());
	}

	@Override
	public EIT_InputTree getInputTree() {
		return compilation._input_tree;
	}

	@Override
	public EIT_ModuleList getModuleList() {
		return moduleList;
	}
}
