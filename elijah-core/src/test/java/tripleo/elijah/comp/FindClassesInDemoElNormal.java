/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Tripleo
 */
public class FindClassesInDemoElNormal {

	@Test
	public final void testListFolders() throws Exception {
		final List<String> args = Helpers.List_of("test/demo-el-normal/listfolders/", "-sE");
		final ErrSink eee = new StdErrSink();
		final Compilation c = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		// searches all modules for top-level Main's that are classes
		final List<ClassStatement> aClassList = c.world().findClass("Main");
		assertEquals(1, aClassList.size());

		assertFalse(MainClassEntryPoint.isMainClass(aClassList.get(0)), "isMainClass");
	}

	@Test
	public final void testListFolders__2() throws Exception {
		final List<String> args = Helpers.List_of("test/demo-el-normal/listfolders/", "-sE");
		final ErrSink eee = new StdErrSink();
		final Compilation c = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		var world = new CrazyRepo(c.world());


		// searches all modules for top-level Main's that are classes
		final List<ClassStatement> aClassList = world.findClass("Main");
		assertEquals(1, aClassList.size());

		assertFalse(MainClassEntryPoint.isMainClass(aClassList.get(0)), "isMainClass");
	}

	@org.junit.jupiter.api.Test
	public final void testParseFile() throws Exception {
		final List<String> args = tripleo.elijah.util.Helpers.List_of("test/demo-el-normal",
				"test/demo-el-normal/main2", "-sE");
		final ErrSink eee = new StdErrSink();
		final Compilation c = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		final List<ClassStatement> aClassList = c.world().findClass("Main");
		for (final ClassStatement classStatement : aClassList) {
			tripleo.elijah.util.Stupidity.println_out_2(classStatement.getPackageName().getName());
		}
		assertEquals(1, aClassList.size()); // NOTE this may change. be aware
	}

	private static class CrazyRepo implements LivingRepo {
		private final LivingRepo d;

		public CrazyRepo(LivingRepo aWorld) {
			d = aWorld;
		}

		@Override
		public Collection<WorldModule> getMods__() {
			return null;
		}

		@Override
		public void _completeModules() {
			d._completeModules();
		}

		@Override
		public LivingClass addClass(ClassStatement cs) {
			return d.addClass(cs);
		}

		@Override
		public DefaultLivingClass addClass(EvaClass aClass, Add addFlag) {
			return d.addClass(aClass, addFlag);
		}

		@Override
		public DefaultLivingFunction addFunction(BaseEvaFunction aFunction, Add aMainFunction) {
			return d.addFunction(aFunction, aMainFunction);
		}

		@Override
		public LivingFunction addFunction(BaseFunctionDef fd) {
			return d.addFunction(fd);
		}

		@Override
		public void addModule(OS_Module mod, String aFilename, Compilation aC) {

			d.addModule(mod, aFilename, aC);
		}

		@Override
		public void addModule2(WorldModule aWorldModule) {

			d.addModule2(aWorldModule);
		}

		@Override
		public void addModuleProcess(CompletableProcess<WorldModule> wmcp) {

			d.addModuleProcess(wmcp);
		}

		@Override
		public DefaultLivingNamespace addNamespace(EvaNamespace aNamespace, Add aNone) {
			return d.addNamespace(aNamespace, aNone);
		}

		@Override
		public LivingPackage addPackage(OS_Package pk) {
			return d.addPackage(pk);
		}

		@Override
		public void eachModule(Consumer<WorldModule> object) {
			d.eachModule(object);
		}

		@Override
		public List<ClassStatement> findClass(String aClassName) {
			final List<ClassStatement> l = new ArrayList<>();
			var modules1 = d.modules().stream().map(WorldModule::module).collect(Collectors.toList());

			var ll = modules1.stream()
					.filter(m -> m.hasClass(aClassName))
					.map(m -> (ClassStatement) m.findClassesNamed(aClassName))
					.collect(Collectors.toList());

			for (final OS_Module module : modules1) {
				if (module.hasClass(aClassName)) {
					l.add((ClassStatement) module.findClassesNamed(aClassName));
				}
			}

			assert Objects.equals(l,ll);

			return l;
		}

		@Override
		public WorldModule findModule(OS_Module mod) {
			return d.findModule(mod);
		}

		@Override
		public LivingClass getClass(EvaClass aEvaClass) {
			return d.getClass(aEvaClass);
		}

		@Override
		public List<LivingClass> getClassesForClassNamed(String string) {
			return d.getClassesForClassNamed(string);
		}

		@Override
		public List<LivingClass> getClassesForClassStatement(ClassStatement cls) {
			return d.getClassesForClassStatement(cls);
		}

		@Override
		public LivingFunction getFunction(BaseEvaFunction aBaseEvaFunction) {
			return d.getFunction(aBaseEvaFunction);
		}

		@Override
		public LivingNamespace getNamespace(EvaNamespace aEvaNamespace) {
			return d.getNamespace(aEvaNamespace);
		}

		@Override
		public OS_Package getPackage(String aPackageName) {
			return d.getPackage(aPackageName);
		}

		@Override
		public boolean hasPackage(String aPackageName) {
			return d.hasPackage(aPackageName);
		}

		@Override
		public OS_Package makePackage(Qualident aPkgName) {
			return d.makePackage(aPkgName);
		}

		@Override
		public Collection<WorldModule> modules() {
			return d.modules();
		}
	}
}

//
//
//
