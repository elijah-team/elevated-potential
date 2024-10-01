package tripleo.elijah_elevated_durable.comp;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.comp.nextgen.i.Asseveration;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.compiler_model.CM_Module;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah_elevateder.nextgen.inputtree.EDL_EIT_ModuleList;

public class EDL_ObjectTree implements CK_ObjectTree {
	private final EDL_Compilation compilation;
	private final EIT_ModuleList  moduleList;

	public EDL_ObjectTree(final EDL_Compilation aCompilation) {
		compilation = aCompilation;
		moduleList  = new EDL_EIT_ModuleList();
	}

	@Override
	public void asseverate(Object o, Asseverate asseveration) {
		switch (asseveration) {
		case CI_PARSED ->  {
			int y=2;
//			throw new UnintendedUseException();
		}
		case ELIJAH_PARSED -> {
			final CM_Module x = (CM_Module)o;

			if (DebugFlags.MakeSense) {
				x.adviseCreator(compilation);
				x.adviseWorld(compilation.world());
			}
		}
		case CI_HASHED -> {
			@SuppressWarnings({ "rawtypes", "unchecked" })
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
		case CI_CACHED -> throw new UnsupportedOperationException("Unimplemented case: " + asseveration);
		case CI_SPECCED -> {}//throw new UnsupportedOperationException("Unimplemented case: " + asseveration);
		case EZ_PARSED -> throw new UnsupportedOperationException("Unimplemented case: " + asseveration);
		default -> throw new IllegalArgumentException("Unexpected value: " + asseveration);
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
		return compilation.getInputTree();
	}

	@Override
	public EIT_ModuleList getModuleList() {
		return moduleList;
	}
}
