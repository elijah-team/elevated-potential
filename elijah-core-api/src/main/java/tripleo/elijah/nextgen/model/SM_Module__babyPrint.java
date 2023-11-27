package tripleo.elijah.nextgen.model;

import org.jetbrains.annotations.NotNull;

public enum SM_Module__babyPrint {
	;

	public static void babyPrint(final @NotNull SM_Module sm) {
		for (final SM_ModuleItem item : sm.items()) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(String.valueOf(item));
		}
	}
}
