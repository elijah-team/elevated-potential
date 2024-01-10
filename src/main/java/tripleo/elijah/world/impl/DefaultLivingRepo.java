package tripleo.elijah.world.impl;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.entrypoints.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class DefaultLivingRepo implements LivingRepo {
	private final @NotNull ObservableCompletableProcess<WorldModule> wmo = new ObservableCompletableProcess<>();

	private final Map<String, OS_Package> _packages = new HashMap<>();
	private final Set<WorldModule> _modules = new HashSet<>();
	private final @NotNull List<LivingNode> repo = new ArrayList<>();
	private final @NotNull Multimap<BaseEvaFunction, DefaultLivingFunction> functionMap = ArrayListMultimap.create();

	private int _classCode = 101;
	private int _functionCode = 1001;
	private int _packageCode = 1;

	@Override
	public Collection<WorldModule> getMods__() {
		return _modules;
	}

	@Override
	public void _completeModules() {
		wmo.onComplete();
	}

	@Override
	public @Nullable LivingClass addClass(final ClassStatement cs) {
		return null;
	}

	@Override
	public @NotNull DefaultLivingClass addClass(final @NotNull EvaClass aClass, final @NotNull Add addFlag) {
		DefaultLivingClass living = null;
		boolean set = false;

		switch (addFlag) {
		case NONE -> {
			LivingClass livingClass = aClass.getLiving();

			if (livingClass == null) {
				livingClass = new DefaultLivingClass(aClass);
				aClass.setLiving(livingClass);
				set = true;

				living = (DefaultLivingClass) livingClass;
			}

			if (livingClass.getCode() == 0) {
				livingClass.setCode(nextClassCode());
			} else {
				if (2 == 3) {
					assert true;
				}
			}
		}
		case MAIN_FUNCTION -> {
			throw new IllegalArgumentException("not a function");
		}
		case MAIN_CLASS -> {
			final boolean isMainClass = MainClassEntryPoint.isMainClass(aClass.getKlass());
			if (!isMainClass) {
				throw new IllegalArgumentException("not a main class");
			}
			aClass.setCode(100);
		}
		}

		if (!set) {
			living = new DefaultLivingClass(aClass);
			aClass.setLiving(living);
		}

		repo.add(living);

		return living;
	}

	@Override
	public @NotNull DefaultLivingFunction addFunction(final @NotNull BaseEvaFunction aFunction,
			final @NotNull Add addFlag) {
		switch (addFlag) {
		case NONE -> {
			aFunction.setCode(nextFunctionCode());
		}
		case MAIN_FUNCTION -> {
			if (aFunction.getFD() instanceof FunctionDef
					&& MainClassEntryPoint.is_main_function_with_no_args((FunctionDef) aFunction.getFD())) {
				aFunction.setCode(1000);
				// compilation.notifyFunction(code, aFunction);
			} else {
				throw new IllegalArgumentException("not a main function");
			}
		}
		case MAIN_CLASS -> {
			throw new IllegalArgumentException("not a class");
		}
		}

		final DefaultLivingFunction living = new DefaultLivingFunction(aFunction);
		aFunction._living = living;

		functionMap.put(aFunction, living);

		return living;
	}

	@Override
	public @Nullable LivingFunction addFunction(final BaseFunctionDef fd) {
		return null;
	}

	@Override
	public void addModule(final OS_Module mod, final String aFilename, final Compilation0 aC) {
		addModule(mod, aFilename, (Compilation) aC);
	}

	//@Override
	public void addModule(final @NotNull OS_Module mod,
	                      final @NotNull String aFilename,
	                      final @NotNull Compilation aC) {
//		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("LivingRepo::addModule >> " + aFilename);

//		var t = aC.getCompilerInputListener();
//		t.change(new EIT_ModuleInput(mod, aC), CompilerInput.CompilerInputField.DIRECTORY_RESULTS);

//		aC._cis().onNext();
	}

	@Override
	public void addModule2(final WorldModule aWorldModule) {
		_modules.add(aWorldModule);

		wmo.onNext(aWorldModule);
	}

	@Override
	public void addModuleProcess(CompletableProcess<WorldModule> wmcp) {
		wmo.subscribe(wmcp);
	}

	@Override
	public @NotNull DefaultLivingNamespace addNamespace(final @NotNull EvaNamespace aNamespace,
			final @NotNull Add addFlag) {
		switch (addFlag) {
		case NONE -> {
			aNamespace.setCode(nextClassCode());
		}
		case MAIN_FUNCTION -> {
			throw new IllegalArgumentException("not a function");
		}
		case MAIN_CLASS -> {
			throw new IllegalArgumentException("not a main class");
		}
		}

		final DefaultLivingNamespace living = new DefaultLivingNamespace(aNamespace);
		aNamespace._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public @Nullable LivingPackage addPackage(final OS_Package pk) {
		return null;
	}

	@Override
	public void eachModule(Consumer<WorldModule> object) {
		_modules.forEach(object);
	}

	@Override
	public List<ClassStatement> findClass(String aClassName) {
		final List<ClassStatement> l = new ArrayList<>();
		var modules1 = modules().stream()
				.map(WorldModule::module)
				.collect(Collectors.toList());

		// TODO idk why I can never figure this out
//		var ll = modules1.stream()
//				.filter(m -> m.hasClass(aClassName))
//				.map(m -> m.findClassesNamed(aClassName))
//				.collect(Collectors.toList());

		for (final OS_Module module : modules1) {
			if (module.hasClass(aClassName)) {
				l.addAll(module.findClassesNamed(aClassName));
			}
		}

//		assert Objects.equals(l,ll);

		return l;
	}

	@Override
	public @Nullable WorldModule findModule(final @NotNull OS_Module mod) {
		// noinspection UnnecessaryLocalVariable
		final WorldModule result = _modules.stream()
				.filter(wm -> wm.module() == mod)
				.findFirst()
				.orElse(null);

		return result;
	}

	@Override
	public @NotNull LivingClass getClass(final @NotNull EvaClass aEvaClass) {
		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingClass livingClass) {
				if (livingClass.evaNode().equals(aEvaClass))
					return livingClass;
			}
		}

		final DefaultLivingClass living = new DefaultLivingClass(aEvaClass);
		// klass._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public @NotNull List<LivingClass> getClassesForClassNamed(final String className) {
		List<LivingClass> lcs = new LinkedList<>();

		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingClass livingClass) {
				if (livingClass.getElement().name().equals(className))
					lcs.add(livingClass);
			}
		}

		return lcs;
	}

	@Override
	public @NotNull List<LivingClass> getClassesForClassStatement(ClassStatement cls) {
		List<LivingClass> lcs = new LinkedList<>();

		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingClass livingClass) {
				if (livingClass.getElement().equals(cls))
					lcs.add(livingClass);
			}
		}

		return lcs;
	}

	@Override
	public @Nullable LivingFunction getFunction(final BaseEvaFunction aBaseEvaFunction) {
		var c = functionMap.get(aBaseEvaFunction);

		if (!c.isEmpty())
			return c.iterator().next();

		return null;
	}

	@Override
	public @NotNull LivingNamespace getNamespace(final EvaNamespace aEvaNamespace) {
		for (LivingNode livingNode : repo) {
			if (livingNode instanceof final @NotNull LivingNamespace livingNamespace) {
				if (livingNamespace.evaNode().equals(aEvaNamespace))
					return livingNamespace;
			}
		}

		final DefaultLivingNamespace living = new DefaultLivingNamespace(aEvaNamespace);
		// klass._living = living;

		repo.add(living);

		return living;
	}

	@Override
	public OS_Package getPackage(final String aPackageName) {
		return _packages.get(aPackageName);
	}

	@Override
	public boolean hasPackage(final @NotNull String aPackageName) {
		if (aPackageName.equals("C")) {
			int y = 2;
		}
		return _packages.containsKey(aPackageName);
	}

	@Override
	public boolean isPackage(final String pkg) {
		return _packages.containsKey(pkg);
	}

	@Override
	public void addModule(final OS_Module aMod, final CM_Filename aFn, final Compilation aCompilation) {
		addModule(aMod, aFn.getString(), aCompilation);
	}

	@Override
	public OS_Package makePackage(final @NotNull Qualident pkg_name) {
		final String pkg_name_s = pkg_name.toString();
		if (isPackage(pkg_name_s)) {
			return _packages.get(pkg_name_s);
		}

		final OS_Package newPackage = new OS_PackageImpl(pkg_name, nextPackageCode());
		_packages.put(pkg_name_s, newPackage);
		return newPackage;
	}

	@Override
	public Collection<WorldModule> modules() {
		return _modules;
	}

	public int nextClassCode() {
		int i = _classCode;
		_classCode++;
		return i;
	}

	public int nextFunctionCode() {
		int i = _functionCode;
		_functionCode++;
		return i;
	}

	@Contract(mutates = "this")
	private int nextPackageCode() {
		int i = _packageCode;
		_packageCode++;
		return i;
	}
}
