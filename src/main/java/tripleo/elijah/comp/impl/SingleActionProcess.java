package tripleo.elijah.comp.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_Process;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class SingleActionProcess implements CB_Process {
	// README tape
	private final CB_Action a;
	private final String    name;

	public SingleActionProcess(final CB_Action aAction, final String aCBFindStdLibProcess) {
		a    = aAction;
		name = aCBFindStdLibProcess;
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
