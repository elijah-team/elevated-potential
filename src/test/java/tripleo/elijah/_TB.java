package tripleo.elijah;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.ElijahTestCli;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.inputs.CompilerInput_;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.world.i.LivingRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

class _TB {
	@NotNull
	private static List<CompilerInput> _convertStringListToInputs(final List<String> ls, final Compilation c) {
		final List<CompilerInput> inputs = new ArrayList<>();
		for (String s : ls) {
			final CompilerInput i1 = new CompilerInput_(s, Optional.of(c));
			inputs.add(i1);
		}
		return inputs;
	}

	public static boolean assertLiveClass(final String aClassName, final String aPackageName, final ElijahTestCli c0) {
		final Compilation          c       = c0.cli.getComp();
		final LivingRepo           world   = c.world();
		final List<ClassStatement> classes = world.findClass(aClassName);

		final Predicate<ClassStatement> predicate = new Predicate<>() {
			@Override
			public boolean test(final ClassStatement classStatement) {
				boolean result;
				if (aPackageName == null) {
					//result = Objects.equals(classStatement.getPackageName(), WorldGlobals.defaultPackage());
					result = classStatement.getPackageName().getName() == null;
				} else {
					result = Helpers.String_equals(classStatement.getPackageName().getName(), aPackageName);
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
}
