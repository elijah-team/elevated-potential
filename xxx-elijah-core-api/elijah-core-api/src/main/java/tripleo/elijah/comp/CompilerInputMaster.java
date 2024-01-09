package tripleo.elijah.comp;

import tripleo.elijah.comp.i.extra.*;

public interface CompilerInputMaster {
	void addListener(CompilerInputListener compilerInputListener);

	void notifyChange(CompilerInput compilerInput, CompilerInput.CompilerInputField compilerInputField);
}
