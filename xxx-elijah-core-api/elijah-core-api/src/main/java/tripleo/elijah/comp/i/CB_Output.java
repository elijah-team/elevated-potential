package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;

import java.util.*;

// 09/27 what the hell is this?
public interface CB_Output {
	@NotNull
	List<CB_OutputString> get();

	void logProgress(int number, String text);

	void print(String s);

	void logProgress(Diagnostic aDiagnostic);
}
