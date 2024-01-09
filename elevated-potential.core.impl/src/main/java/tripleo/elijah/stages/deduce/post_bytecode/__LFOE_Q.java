package tripleo.elijah.stages.deduce.post_bytecode;

import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.deduce.NamespaceInvocation;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class __LFOE_Q implements _LFOE_Q {
	private final @NotNull GenerateFunctions generateFunctions;
	private final          WorkList          wl;
	private final @NotNull DeduceTypes2      deduceTypes2;

	public __LFOE_Q(WorkManager aWorkManager, WorkList aWorkList, final @NotNull DeduceTypes2 aDeduceTypes2) {
		generateFunctions = aDeduceTypes2.phase.generatePhase.getGenerateFunctions(aDeduceTypes2.module);
		wl                = aWorkList;
		this.deduceTypes2 = aDeduceTypes2;
	}

	@Override
	public void enqueue_ctor(final GenerateFunctions generateFunctions1, final @NotNull FunctionInvocation fi,
							 final IdentExpression aConstructorName) {
		// assert generateFunctions1 == generateFunctions;
		final WlGenerateCtor wlgf = deduceTypes2._inj().new_WlGenerateCtor(generateFunctions, fi, aConstructorName,
																		   deduceTypes2.phase.getCodeRegistrar());
		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_default_ctor(final GenerateFunctions generateFunctions1,
									 final @NotNull FunctionInvocation fi,
									 final Consumer<Eventual<BaseEvaFunction>> cef) {
		// assert generateFunctions1 == generateFunctions;
		final ICodeRegistrar codeRegistrar = deduceTypes2._phase().getCodeRegistrar();
		enqueue_default_ctor(generateFunctions1, fi, codeRegistrar, cef);
	}

	@Override
	public void enqueue_default_ctor(final GenerateFunctions generateFunctions1,
									 final @NotNull FunctionInvocation fi,
									 final ICodeRegistrar codeRegistrar,
									 final Consumer<Eventual<BaseEvaFunction>> cef) {
		assert generateFunctions1 == generateFunctions;
		final WlGenerateDefaultCtor wlgf = deduceTypes2._inj().new_WlGenerateDefaultCtor(generateFunctions,
																						 fi,
																						 deduceTypes2.creationContext(),
																						 codeRegistrar);

		Eventual<BaseEvaFunction> x = wlgf.getGenerated();
		if (cef != null) cef.accept(x);

		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_function(final GenerateFunctions generateFunctions1, final @NotNull FunctionInvocation fi,
								 final ICodeRegistrar cr) {
		// assert generateFunctions1 == generateFunctions;
		final WlGenerateFunction wlgf = deduceTypes2._inj().new_WlGenerateFunction(generateFunctions, fi, cr);
		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_function(final @NotNull Supplier<GenerateFunctions> som,
								 final @NotNull FunctionInvocation aFi, final ICodeRegistrar cr) {
		final WlGenerateFunction wlgf = deduceTypes2._inj().new_WlGenerateFunction(som.get(), aFi, cr);
		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_namespace(final @NotNull Supplier<GenerateFunctions> som,
								  final @NotNull NamespaceInvocation aNsi, final DeducePhase.GeneratedClasses aGeneratedClasses,
								  final ICodeRegistrar aCr) {
		wl.addJob(deduceTypes2._inj().new_WlGenerateNamespace(som.get(), aNsi, aGeneratedClasses, aCr));
	}
}
