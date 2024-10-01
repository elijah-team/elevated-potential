package tripleo.elijah_elevateder.nextgen.outputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevateder.nextgen.inputtree.EIT_Input_HashSourceFile_Triple;

import java.util.*;

public class EOT_OutputFileImpl implements EOT_OutputFile {
	private final @NotNull EOT_FileNameProvider                  _filename;
	private final          List<EIT_Input>                       _inputs = new ArrayList<>();
	private final @NotNull EOT_OutputType                        _type;
	private final @NotNull EG_Statement                          _sequence; // TODO List<?> ??
	public                 List<EIT_Input_HashSourceFile_Triple> x;

	public EOT_OutputFileImpl(final @NotNull List<EIT_Input> inputs, final @NotNull EOT_FileNameProvider filename,
							  final @NotNull EOT_OutputType type, final @NotNull EG_Statement sequence) {
		_filename = filename;
		_type = type;
		_sequence = sequence;
		_inputs.addAll(inputs);
	}

	public EOT_OutputFileImpl(final @NotNull List<EIT_Input> inputs, final @NotNull String filename,
							  final @NotNull EOT_OutputType type, final @NotNull EG_Statement sequence) {
		this(inputs, new _U_OF.DefaultFileNameProvider(filename), type, sequence);
	}

	@Override
	public String getFilename() {
		return _filename.getFilename();
	}

	@Override
	public @NotNull List<EIT_Input> getInputs() {
		return _inputs;
	}

	@Override
	public EG_Statement getStatementSequence() {
		return _sequence;
	}

	@Override
	public EOT_OutputType getType() {
		return _type;
	}

	@Override
	public String toString() {
		return "(%s) '%s'".formatted(_type, _filename.getFilename());
	}

	// rules/constraints whatever
}
