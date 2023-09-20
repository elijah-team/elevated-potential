package tripleo.elijah.comp;

import tripleo.elijah.comp.internal.*;

public interface CompilerInputMaster {
	void notifyChange(CompilerInput compilerInput, CompilerInput.CompilerInputField compilerInputField);

	void addListener(CompilerInputListener compilerInputListener);
}
