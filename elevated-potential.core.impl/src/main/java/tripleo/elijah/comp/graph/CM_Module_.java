package tripleo.elijah.comp.graph;

import java.io.InputStream;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.compiler_model.CM_Module;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.g.GLivingRepo;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

public class CM_Module_ implements CM_Module {
	private Operation2<OS_Module> moduleOperation;
	private ElijahSpec spec;
	private WorldModule worldModule;
	private Compilation compilation;

	@Override
	public void advise(final ElijahSpec aSpec) {
		spec = aSpec;
	}

	@Override
	public void advise(final Operation2<OS_Module> aModuleOperation) {
		if (moduleOperation == null) {
			moduleOperation = aModuleOperation;
		} else {
			//throw new AssertionError();
		}
	}

	@Override
	public GWorldModule adviseCreator(final Compilation0 aCompilation0) {
		final OS_Module   module      = moduleOperation.success();

		if (this.compilation == null) {
			this.compilation = (Compilation) aCompilation0;
		}

		worldModule = compilation.con().createWorldModule(module);

		return worldModule;
	}

	@Override
	public void adviseWorld(final GLivingRepo aWorld) {
		((LivingRepo) aWorld).addModule2(worldModule);
	}

	@Override
	public void advise(final LibraryStatementPart aLsp) {
		final OS_Module mm = worldModule.module();

		if (mm.getLsp() != null) {
			//assert false;
		} else {
			mm.setLsp(aLsp);
		}
	}

	@Override
	public void advise(final PreludeProvider prludeProvider) {
		final OS_Module             mm      = worldModule.module();
		final Operation2<OS_Module> pl      = prludeProvider.getOperation();
		final OS_Module             prelude = pl.success();

		// NOTE Go. infectious. tedious. also slightly lazy
		assert pl.mode() == Mode.SUCCESS;

		mm.setPrelude(prelude);
	}

	@Override
	public InputStream s() {
		throw new UnintendedUseException();
	}

	@Override
	public EIT_ModuleInput getEITInput() {
		if (this.compilation != null) {
			// FIXME 12/07 assuming these two are together
			return this.compilation.con().createModuleInput(this.worldModule.module());
		}
		return null;
	}

	@Override
	public String toString() {
		final String moduleOperationS;
		if (moduleOperation != null) {
			moduleOperationS = String.format("%s %s", moduleOperation.mode(), moduleOperation.success());
		} else {
			moduleOperationS = "NULL";
		}

		return "CM_Module_{" +
				"moduleOperation=" + moduleOperationS +
				", spec=" + spec +
				", worldModule=" + worldModule +
				'}';
	}
}
