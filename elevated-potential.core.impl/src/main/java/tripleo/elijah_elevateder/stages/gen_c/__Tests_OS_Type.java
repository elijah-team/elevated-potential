package tripleo.elijah_elevateder.stages.gen_c;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

enum __Tests_OS_Type {
	;

	static String boundedClassName_NormalTypeName(final @NotNull OS_Type ty) {
		final ClassStatement el = ty.getClassOf();
		final String name;
		if (ty instanceof NormalTypeName) {
			name = ((NormalTypeName) ty).getName();
		} else {
			name = el.getName();
		}
		return name;
	}
}
