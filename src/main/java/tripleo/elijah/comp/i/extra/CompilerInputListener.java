package tripleo.elijah.comp.i.extra;

import tripleo.elijah.comp.inputs.CompilerInput;

public interface CompilerInputListener {
	default void baseNotify(CompilerInput compilerInput, CompilerInput.CompilerInputField compilerInputField) {
		change(compilerInput, compilerInputField);
	}

	void change(CompilerInput i, CompilerInput.CompilerInputField field);
}
