package tripleo.elijah.comp.i;

import java.util.List;

public interface CB_Process {
	List<CB_Action> steps();

	String name();

	default void execute(ICompilationBus aCompilationBus) {
		final CB_Monitor monitor = aCompilationBus.getMonitor();

		for (final CB_Action action : steps()) {
			// TODO something about outputStrings
			//  don't have any mercy here
			action.execute(monitor);
		}
	}
}
