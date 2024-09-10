package tripleo.elijah;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.util.Helpers;

import java.util.function.Predicate;

public class _TestBasicHelper {
	public static boolean assertLiveClass(final String aMainLogic, final String aS, final Compilation0 aC) {
		CompilationImpl c     = (CompilationImpl) aC;
		var             ce    = c.getCompilationEnclosure();
		var             world = c.world();

		var classes = world.findClass(aMainLogic);

		var xy = ce.getCompilation();

		final Predicate<ClassStatement> predicate = new Predicate<>() {
			@Override
			public boolean test(final ClassStatement classStatement) {
				boolean result;
				if (aS == null) {
					//result = Objects.equals(classStatement.getPackageName(), WorldGlobals.defaultPackage());
					result = classStatement.getPackageName().getName() == null;
				} else {
					result = Helpers.String_equals(classStatement.getPackageName().getName(), aS);
				}
				return result;
			}
		};

		//noinspection UnnecessaryLocalVariable,SimplifyStreamApiCallChains
		boolean result = classes.stream()
				.filter(predicate)
				.findAny()
				.isPresent();

		return result;
	}

	private boolean assertLiveNsMemberVariable(final String aClassName, final String aNsMemberVariablName, final Compilation0 c) {
		return false;
	}

	private boolean assertLiveConstructor(final String aClassName, final Compilation0 c) {
		return false;
	}

	private boolean assertLiveFunction(final String aClassName, final String aFunctionName, final Compilation0 c) {
		return false;
	}

}
