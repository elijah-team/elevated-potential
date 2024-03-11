package tripleo.elijah_durable_elevated.elijah.stages.deduce.fluffy.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah_durable_elevated.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.fluffy.i.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FluffyModuleImpl implements FluffyModule {
	static class FluffyModuleImplInjector {

		public MainClassEntryPoint new_MainClassEntryPoint(ClassStatement x) {
			return new MainClassEntryPoint(x);
		}
	}

	/**
	 * If classStatement is a "main class", send to consumer
	 *
	 * @param classStatement
	 * @param ccs
	 */
	private static void faep_002(final @NotNull ClassStatement classStatement, final Consumer<ClassStatement> ccs) {
		final List<OS_NamedElement> x = classStatement.findFunction("main");
		final Stream<FunctionDef> found = x.stream().filter(FluffyCompImpl::isMainClassEntryPoint)
				.map(x7 -> (FunctionDef) x7);

//		final int eps = aModule.entryPoints.size();

		found.map(aFunctionDef -> (ClassStatement) aFunctionDef.getParent()).forEach(ccs);

//		assert aModule.entryPoints.size() == eps || aModule.entryPoints.size() == eps+1; // TODO this will fail one day

//		tripleo.elijah.util.Stupidity.println2("243 " + entryPoints +" "+ _fileName);
//		break; // allow for "extend" class
	}

	private final Compilation compilation;

	private final OS_Module module;

	private final FluffyModuleImplInjector __inj = new FluffyModuleImplInjector();

	public FluffyModuleImpl(final OS_Module aModule, final Compilation aCompilation) {
		module = aModule;
		compilation = aCompilation;
	}

	private FluffyModuleImplInjector _inj() {
		return this.__inj;
	}

	@Override
	public void find_all_entry_points() {
		//
		// FIND ALL ENTRY POINTS (should only be one per module)
		//
		final Consumer<ClassStatement> ccs = (x) -> module.entryPoints().add(_inj().new_MainClassEntryPoint(x));

		module.getItems().stream().filter(item -> item instanceof ClassStatement)
				.filter(classStatement -> MainClassEntryPoint.isMainClass((ClassStatement) classStatement))
				.forEach(classStatement -> faep_002((ClassStatement) classStatement, ccs));
	}

	@Override
	public void find_multiple_items(final @NotNull FluffyComp aFluffyComp, OS_ModuleImpl.Complaint c) {
		aFluffyComp.find_multiple_items(module, c);
	}

	@Override
	public @Nullable FluffyLsp lsp() {
		return null;
	}

	@Override
	public @Nullable List<FluffyMember> members() {
		return null;
	}

	@Override
	public @Nullable String name() {
		return null;
	}
}
