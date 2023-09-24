package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.i.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class CB_FindStdLibProcess implements CB_Process {
	private final CB_FindStdLibAction action;

	public CB_FindStdLibProcess(CompilationEnclosure ce, CompilationRunner cr) {
		action = new CB_FindStdLibAction(ce, cr);
	}

	@Override
	public List<CB_Action> steps() {
		return List_of(action);
	}
}
