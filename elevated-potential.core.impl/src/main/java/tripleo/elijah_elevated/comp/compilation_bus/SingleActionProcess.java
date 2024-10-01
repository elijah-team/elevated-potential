package tripleo.elijah_elevated.comp.compilation_bus;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_Process;

import java.util.List;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

public class SingleActionProcess implements CB_Process {
	// README tape
	private final CB_Action a;
	private final String    name;

	public SingleActionProcess(final CB_Action aProcessAction, final String aProcessName) {
		a    = aProcessAction;
		name = aProcessName;
	}

	@Override
	public @NotNull List<CB_Action> steps() {
		return List_of(a);
	}

	@Override
	public String name() {
		return name;//"SingleActionProcess";
	}
}
