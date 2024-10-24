package tripleo.elijah_elevateder.entrypoints;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_elevated_durable.lang_impl.BaseFunctionDef;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_elevateder.world.i.LivingRepo;

public interface EntryPointProcessor {
	class EPP_AFEP implements EntryPointProcessor {

		private final ArbitraryFunctionEntryPoint afep;
		private final EPP_MCEP.@NotNull EppCodeRegistrar codeRegistrar;
		private final DeducePhase       deducePhase;
		private final GenerateFunctions generateFunctions;
		private final WorkList          wl;

		public EPP_AFEP(final ArbitraryFunctionEntryPoint aEp, final DeducePhase aDeducePhase, final WorkList aWl,
				final GenerateFunctions aGenerateFunctions) {
			afep = aEp;
			deducePhase = aDeducePhase;
			wl = aWl;
			generateFunctions = aGenerateFunctions;

			codeRegistrar = new EPP_MCEP.EppCodeRegistrar(deducePhase._compilation());
		}

		@Override
		public void process() {
			final @NotNull FunctionDef f = afep.getFunction();
			@NotNull
			final ClassInvocation ci = deducePhase.registerClassInvocation((ClassStatement) afep.getParent(), null);

			final WlGenerateClass job = new WlGenerateClass(generateFunctions, ci, deducePhase.generatedClasses,
					/* , deducePhase.codeRegistrar */codeRegistrar);
			wl.addJob(job);

			final @NotNull FunctionInvocation fi = deducePhase.newFunctionInvocation((BaseFunctionDef) f, null, ci);
//				fi.setPhase(deducePhase);
			final WlGenerateFunction job1 = new WlGenerateFunction(generateFunctions, fi, codeRegistrar);
			wl.addJob(job1);
		}
	}

	class EPP_MCEP implements EntryPointProcessor {
		final @NotNull ICodeRegistrar codeRegistrar;
		private final  DeducePhase    deducePhase;
		private final GenerateFunctions generateFunctions;
		private final MainClassEntryPoint mcep;
		private final WorkList wl;

		public EPP_MCEP(final MainClassEntryPoint aEp, final DeducePhase aDeducePhase, final WorkList aWl,
				final GenerateFunctions aGenerateFunctions) {
			mcep = aEp;
			deducePhase = aDeducePhase;
			wl = aWl;
			generateFunctions = aGenerateFunctions;

			codeRegistrar = new EppCodeRegistrar(deducePhase._compilation());
		}

		@Override
		public void process() {
			final @NotNull ClassStatement cs = mcep.getKlass();
			final @NotNull FunctionDef f = mcep.getMainFunction();
			final ClassInvocation ci = deducePhase.registerClassInvocation(cs, null, new NULL_DeduceTypes2());

			assert ci != null;

			deducePhase.generatePhase.setCodeRegistrar(codeRegistrar);

			final ICodeRegistrar cr = codeRegistrar;

			final @NotNull WlGenerateClass job = new WlGenerateClass(generateFunctions, ci,
					deducePhase.generatedClasses, cr);
			wl.addJob(job);

			final @NotNull FunctionInvocation fi = deducePhase.newFunctionInvocation((BaseFunctionDef) f, null, ci);
//				fi.setPhase(deducePhase);
			final @NotNull WlGenerateFunction job1 = new WlGenerateFunction(generateFunctions, fi, cr);
			wl.addJob(job1);
		}
	}

	class EppCodeRegistrar implements ICodeRegistrar {
		private final Compilation compilation;

		public EppCodeRegistrar(Compilation aC) {
			compilation = aC;
		}

		@Override
		public void registerClass(final EvaClass aClass) {
			compilation.world().addClass(aClass, LivingRepo.Add.MAIN_CLASS);
		}

		@Override
		public void registerClass1(final EvaClass aClass) {
			compilation.world().addClass(aClass, LivingRepo.Add.NONE);
		}

		@Override
		public void registerFunction(final BaseEvaFunction aFunction) {
			compilation.world().addFunction(aFunction, LivingRepo.Add.MAIN_FUNCTION);
		}

		@Override
		public void registerFunction1(final BaseEvaFunction aFunction) {
			compilation.world().addFunction(aFunction, LivingRepo.Add.NONE);
		}

		@Override
		public void registerNamespace(final EvaNamespace aNamespace) {
			compilation.world().addNamespace(aNamespace, LivingRepo.Add.NONE);
		}
	}

	static @NotNull EntryPointProcessor dispatch(final EntryPoint ep, final DeducePhase aDeducePhase,
	                                             final WorkList aWl, final GenerateFunctions aGenerateFunctions) {
		if (ep instanceof MainClassEntryPoint) {
			return new EPP_MCEP((MainClassEntryPoint) ep, aDeducePhase, aWl, aGenerateFunctions);
		} else if (ep instanceof ArbitraryFunctionEntryPoint) {
			return new EPP_AFEP((ArbitraryFunctionEntryPoint) ep, aDeducePhase, aWl, aGenerateFunctions);
		}

		throw new IllegalStateException();
	}

	void process();

}
