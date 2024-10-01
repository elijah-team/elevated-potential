package tripleo.elijah_elevateder.stages.gen_c;

import tripleo.elijah.lang.i.*;

import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.util.Helpers0;

class __Tests_BaseEvaFunction {
	public static boolean testIsValue(final BaseEvaFunction gf, final String name) {
		if (!name.equals("Value")) return false;
		//
		FunctionDef fd = (FunctionDef) gf.getFD();
		switch (fd.getSpecies()) {
		case REG_FUN:
		case DEF_FUN:
			if (!(fd.getParent() instanceof ClassStatement)) return false;
			for (AnnotationPart anno : ((ClassStatement) fd.getParent()).annotationIterable()) {
				if (anno.annoClass().equals(Helpers0.string_to_qualident("Primitive"))) {
					return true;
				}
			}
			return false;
		case PROP_GET:
		case PROP_SET:
			return true;
		default:
			throw new IllegalStateException("Unexpected value: " + fd.getSpecies());
		}
	}

	public static boolean testIsValue(final WhyNotGarish_Function yf, final String name) {
		return testIsValue(yf.getGf(), name);
	}
}
