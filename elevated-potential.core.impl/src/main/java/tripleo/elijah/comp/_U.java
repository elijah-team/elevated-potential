package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.comp_model.*;
import tripleo.elijah_elevated.comp.input.*;

import java.util.*;
import java.util.stream.*;

public class _U {

	public static @NotNull List<@NotNull CompilerInput> stringListToInputList(final @NotNull List<String> args, final @NotNull Compilation aCompilation) {
		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput    input = new CompilerInput_(s, Optional.of(aCompilation));
					final CM_CompilerInput cm    = aCompilation.get(input);
					if (cm.inpSameAs(s)) {
						input.setSourceRoot();
					} else {
						assert false;
					}
					return input;
				})
				.collect(Collectors.toList());
		return inputs;
	}
}
