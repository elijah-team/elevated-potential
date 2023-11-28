package tripleo.elijah.comp.graph;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation0;

import tripleo.elijah.comp.specs.ElijahSpec;

import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.g.GLivingRepo;

import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.i.LivingRepo;

import tripleo.elijah.util.Operation2;

public class CM_Module_ implements CM_Module {
	private Operation2<OS_Module> moduleOperation;
	private ElijahSpec            spec;
	private WorldModule           worldModule;

	@Override
	public void advise(final ElijahSpec aSpec) {
		spec = aSpec;
	}

	@Override
	public void advise(final Operation2<OS_Module> aModuleOperation) {
		moduleOperation = aModuleOperation;
	}

	@Override
	public GWorldModule adviseCreator(final Compilation0 aCompilation0) {
		final OS_Module   module      = moduleOperation.success();
		final Compilation compilation = (Compilation) aCompilation0;

		worldModule = compilation.con().createWorldModule(module);

		return worldModule;
	}

	@Override
	public void adviseWorld(final GLivingRepo aWorld) {
		((LivingRepo)aWorld).addModule2(worldModule);
	}
}
