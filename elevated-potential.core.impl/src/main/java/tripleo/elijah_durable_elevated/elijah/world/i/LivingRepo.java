package tripleo.elijah_durable_elevated.elijah.world.i;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.g.GLivingRepo;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah_durable_elevated.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.world.impl.DefaultLivingNamespace;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface LivingRepo extends GLivingRepo {
	Collection<WorldModule> getMods__();

	boolean isPackage(String aPkgName);

	enum Add {
		MAIN_CLASS, MAIN_FUNCTION, NONE
	}

	void _completeModules();

	LivingClass addClass(ClassStatement cs);

	LivingClass addClass(EvaClass aClass, Add addFlag);

	LivingFunction addFunction(BaseEvaFunction aFunction, Add aMainFunction);

	LivingFunction addFunction(BaseFunctionDef fd);

	void addModule(OS_Module mod, String aFilename, final Compilation0 aC);

	// DefaultLivingClass addClass(EvaClass aClass, Add aMainClass);

	void addModule2(WorldModule aWorldModule);

	void addModuleProcess(CompletableProcess<WorldModule> wmcp);

	DefaultLivingNamespace addNamespace(EvaNamespace aNamespace, Add aNone);

	LivingPackage addPackage(OS_Package pk);

	void eachModule(Consumer<WorldModule> object);

	List<ClassStatement> findClass(String main);

	@Nullable WorldModule findModule(final @NotNull OS_Module mod);

	LivingClass getClass(EvaClass aEvaClass);

	List<LivingClass> getClassesForClassNamed(String string);

	List<LivingClass> getClassesForClassStatement(ClassStatement cls);

	LivingFunction getFunction(BaseEvaFunction aBaseEvaFunction);

	LivingNamespace getNamespace(EvaNamespace aEvaNamespace);

	OS_Package getPackage(String aPackageName);

	boolean hasPackage(String aPackageName);

	OS_Package makePackage(Qualident aPkgName);

	Collection<WorldModule> modules();
}
