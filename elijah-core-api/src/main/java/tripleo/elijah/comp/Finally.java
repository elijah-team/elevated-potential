package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.outputtree.*;

import java.util.*;

public interface Finally {
	int codeOutputSize();

	List<String> getCodeOutputs();

	int inputCount();

	int outputCount();

	void addCodeOutput(EOT_FileNameProvider aFileNameProvider, EOT_OutputFile aOff);

	void addInput(Nameable aNameable, Out2 ty);

	boolean containsCodeOutput(@NotNull String s);

	boolean containsInput(String aS);

	boolean outputOn(Outs aOuts);

	void turnAllOutputOff();

	void turnOutputOff(Outs aOut);

	enum Out2 {
		EZ, ELIJAH
	}

	enum Outs {
		Out_6262, Out_727, Out_350, Out_364, Out_252, Out_2121, Out_486, Out_5757, Out_1069, Out_141, Out_EVTE_159,
		Out_401b
	}

	interface Nameable {
		String getName();
	}

}
