package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;

public enum ProcessElement {
	;

	public static void processElement(@Nullable OS_Element el, @NotNull IElementProcessor ep) {
		if (el == null)
			ep.elementIsNull();
		else
			ep.hasElement(el);
	}
}
