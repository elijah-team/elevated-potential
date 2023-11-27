package tripleo.elijah.comp.internal;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

public class DefaultObjectTree implements CK_ObjectTree {
	private final CompilationImpl compilation;
	private       EIT_ModuleList  moduleList;

	public DefaultObjectTree(final CompilationImpl aCompilation) {
		compilation = aCompilation;
		moduleList  = new EIT_ModuleList_();
	}

	@Override
	public void asseverate(Object o, Asseverate asseveration) {
		switch (asseveration) {
		case CI_PARSED ->  {
			throw new UnintendedUseException();
		}
		case ELIJAH_PARSED -> {
			var x = (Pair<ElijahSpec, Operation2<OS_Module>>)o;

			ElijahSpec           spec = x.getLeft();
			Operation2<OS_Module> calm = x.getRight();
			PipelineLogic        pl   = getCompilationEnclosure().getPipelineLogic();
			WorldModule          wm   = compilation.con().createWorldModule(calm.success());

			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("**************************************************Comp ELIJAH_PARSED  "+wm.module().getFileName());

			if (DebugFlags.MakeSense) {
				final WorldModule worldModule = compilation.con().createWorldModule(calm.success());
				compilation.world().addModule2(worldModule);
			}
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

	private CompilationEnclosure getCompilationEnclosure() {
		return this.compilation.getCompilationEnclosure();
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
