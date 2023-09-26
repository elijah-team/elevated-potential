package tripleo.elijah.world.i;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.BaseFunctionDef;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah.world.impl.DefaultLivingClass;
import tripleo.elijah.world.impl.DefaultLivingFunction;
import tripleo.elijah.world.impl.DefaultLivingNamespace;

import java.util.Collection;
import java.util.List;
import java.util.function.*;

public interface LivingRepo {
	Collection<WorldModule> getMods__();

	enum Add {
		MAIN_CLASS, MAIN_FUNCTION, NONE
	}

	void _completeModules();

	LivingClass addClass(ClassStatement cs);

	DefaultLivingClass addClass(EvaClass aClass, Add addFlag);

	DefaultLivingFunction addFunction(BaseEvaFunction aFunction, Add aMainFunction);

	LivingFunction addFunction(BaseFunctionDef fd);

	void addModule(OS_Module mod, String aFilename, final Compilation aC);

	// DefaultLivingClass addClass(EvaClass aClass, Add aMainClass);

	void addModule2(WorldModule aWorldModule);

	void addModuleProcess(CompletableProcess<WorldModule> wmcp);

	DefaultLivingNamespace addNamespace(EvaNamespace aNamespace, Add aNone);

	LivingPackage addPackage(OS_Package pk);

	void eachModule(Consumer<WorldModule> object);

	List<ClassStatement> findClass(String main);

	WorldModule findModule(OS_Module mod);

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
