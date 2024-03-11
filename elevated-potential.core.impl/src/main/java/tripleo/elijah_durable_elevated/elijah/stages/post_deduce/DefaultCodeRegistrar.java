package tripleo.elijah_durable_elevated.elijah.stages.post_deduce;

import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_elevated.elijah.world.i.LivingRepo;

public class DefaultCodeRegistrar implements ICodeRegistrar {
	private final Compilation compilation;

	public DefaultCodeRegistrar(final Compilation aC) {
		compilation = aC;
	}

	private LivingRepo getLivingRepo() {
		return compilation.world();
	}

	@Override
	public void registerClass(final EvaClass aClass) {
		getLivingRepo().addClass(aClass, LivingRepo.Add.MAIN_CLASS);
	}

	@Override
	public void registerClass1(final EvaClass aClass) {
		getLivingRepo().addClass(aClass, LivingRepo.Add.NONE);
	}

	@Override
	public void registerFunction(final BaseEvaFunction aFunction) {
		getLivingRepo().addFunction(aFunction, LivingRepo.Add.MAIN_FUNCTION);
	}

	@Override
	public void registerFunction1(final BaseEvaFunction aFunction) {
		getLivingRepo().addFunction(aFunction, LivingRepo.Add.NONE);
	}

	@Override
	public void registerNamespace(final EvaNamespace aNamespace) {
		getLivingRepo().addNamespace(aNamespace, LivingRepo.Add.NONE);
	}
}
