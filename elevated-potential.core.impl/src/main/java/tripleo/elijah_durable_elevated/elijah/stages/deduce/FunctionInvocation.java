/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;
import tripleo.elijah_durable_elevated.elijah.lang.impl.LangGlobals;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode.__LFOE_Q;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.work.WorkList__;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 1/21/21 9:04 PM
 */
public class FunctionInvocation implements IInvocation {
	public final  ProcTableEntry            pte;
	final         FunctionDef               fd;
	private final Eventual<BaseEvaFunction> generateDeferred = new Eventual<>();
	public        CI_Hint                   hint;
	private @Nullable BaseEvaFunction     _generated       = null;
	@lombok.Setter
	@Getter
	private           NamespaceInvocation namespaceInvocation;
	@lombok.Setter
	@Getter
	private           ClassInvocation     classInvocation;

	public FunctionInvocation(FunctionDef aFunctionDef, ProcTableEntry aProcTableEntry, @NotNull IInvocation invocation,
							  GeneratePhase phase) {
		this.fd  = aFunctionDef;
		this.pte = aProcTableEntry;
		assert invocation != null;
		invocation.setForFunctionInvocation(this);
//		setPhase(deducePhase);
	}

	/*
	 * public void setPhase(final GeneratePhase generatePhase) { if (pte != null)
	 * pte.completeDeferred().then(new DoneCallback<ProcTableEntry>() {
	 *
	 * @Override public void onDone(ProcTableEntry result) {
	 * makeGenerated(generatePhase, null); } }); else makeGenerated(generatePhase,
	 * null); }
	 */

	public WlGenerateFunction generateFunction(final DeduceTypes2 aDeduceTypes2, final OS_Element aBest) {
		throw new IllegalStateException("Error");
	}

	public Eventual<BaseEvaFunction> generateDeferred() {
		return generateDeferred;
	}

	public Eventual<BaseEvaFunction> generatePromise() {
		return generateDeferred;
	}

	public List<TypeTableEntry> getArgs() {
		if (pte == null)
			return List_of();
		return pte.args;
	}

	public @Nullable BaseEvaFunction getEva() {
		throw new UnintendedUseException(); // TODO 10/15
		//return null; // TODO 04/15
	}

	public FunctionDef getFunction() {
		return fd;
	}

	public @Nullable BaseEvaFunction getGenerated() {
		return _generated;
	}

	public void setGenerated(BaseEvaFunction aGeneratedFunction) {
		_generated = aGeneratedFunction;
	}

	public Eventual<BaseEvaFunction> makeGenerated__Eventual(final @NotNull DeduceCreationContext cl,
															 final EventualRegister register) {
		final DeduceTypes2              deduceTypes2 = cl.getDeduceTypes2();
		final Eventual<BaseEvaFunction> eef          = new Eventual<>();

		if (register != null) {
			eef.register(register);
		}

		@Nullable OS_Module module = null;
		if (fd != null) {
			final Context ___fd_context = fd.getContext();
			if (___fd_context != null) {
				if (___fd_context.getParent() != null)
					module = ___fd_context.module();
			}
		}
		if (module == null)
			module = classInvocation.getKlass().getContext().module(); // README for constructors

		// TODO 10/15 is this q?; 10/17 yes, now find a way to use it
		final __LFOE_Q                          q        = new __LFOE_Q(null, new WorkList__(), deduceTypes2);
		final DeduceTypes2.DeduceTypes2Injector injector = deduceTypes2._inj();

		if (fd == LangGlobals.defaultVirtualCtor) {
			xxx___forDefaultVirtualCtor(cl, injector, module).then(eef::resolve);
			return eef;
		} else if (fd instanceof ConstructorDef cd) {
			eef.resolve(xxxForConstructorDef(cl, cd, injector, module));
			return eef;
		} else {
			eef.resolve(xxx__forFunction(cl, injector, module));
			return eef;
		}

		// {
		// eef.fail(null);
		// return eef;
		// }
	}

	private Eventual<BaseEvaFunction> xxx___forDefaultVirtualCtor(final @NotNull DeduceCreationContext cl,
																  final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
																  final @NotNull OS_Module module) {
		final @NotNull WlGenerateDefaultCtor wlgdc = injector.new_WlGenerateDefaultCtor(module, this, cl);
		wlgdc.run(null);
		return wlgdc.getGenerated();
	}

	@NotNull
	private BaseEvaFunction xxxForConstructorDef(final @NotNull DeduceCreationContext cl,
												 final @NotNull ConstructorDef cd,
												 final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
												 final @NotNull OS_Module module) {
		final @NotNull WlGenerateCtor wlgf = injector.new_WlGenerateCtor(module, cd.getNameNode(), this, cl);
		wlgf.run(null);

		final BaseEvaFunction gf = wlgf.getResult();
		return gf;
	}

	@NotNull
	private BaseEvaFunction xxx__forFunction(final @NotNull DeduceCreationContext cl,
											 final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
											 final @NotNull OS_Module module) {

		final GeneratePhase generatePhase = cl.getGeneratePhase();
		final DeducePhase   deducePhase   = cl.getDeducePhase();

		@NotNull
		WlGenerateFunction wlgf = injector.new_WlGenerateFunction(module, this, cl);

		wlgf.run(null);

		EvaFunction gf = wlgf.getResult();

		if (gf.getGenClass() == null) {
			if (namespaceInvocation != null) {
				// namespaceInvocation =
				// deducePhase.registerNamespaceInvocation(namespaceInvocation.getNamespace());

				@NotNull
				WlGenerateNamespace wlgn = injector.new_WlGenerateNamespace(generatePhase.getGenerateFunctions(module),
																			namespaceInvocation,
																			deducePhase.generatedClasses,
																			deducePhase.getCodeRegistrar());
				wlgn.run(null);
				int y = 2;
			}
		}

		return gf;
	}

	@Override
	public void setForFunctionInvocation(final FunctionInvocation aFunctionInvocation) {
		throw new IllegalStateException("maybe this shouldn't be done?");
	}

	public NamespaceInvocation getNamespaceInvocation() {
		// 24/01/04 back and forth
		return this.namespaceInvocation;
	}

	public ClassInvocation getClassInvocation() {
		// 24/01/04 back and forth
		return this.classInvocation;
	}

	public void setClassInvocation(ClassInvocation classInvocation2) {
		// 24/01/04 back and forth
		this.classInvocation = classInvocation2;
	}

	public void setNamespaceInvocation(NamespaceInvocation namespaceInvocation2) {
		// 24/01/04 back and forth
		this.namespaceInvocation = namespaceInvocation2;
	}
}

//
//
//
