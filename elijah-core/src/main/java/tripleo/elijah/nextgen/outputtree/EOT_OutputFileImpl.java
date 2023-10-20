package tripleo.elijah.nextgen.outputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;

import java.util.*;

public class EOT_OutputFileImpl implements EOT_OutputFile {
	public static class DefaultFileNameProvider implements FileNameProvider {
		private final String r;

		public DefaultFileNameProvider(final String aR) {
			r = aR;
		}

		@Override
		public String getFilename() {
			return r;
		}
	}

	private final @NotNull FileNameProvider _filename;
	private final List<EIT_Input> _inputs = new ArrayList<>();
	private final @NotNull EOT_OutputType _type;
	private final @NotNull EG_Statement _sequence; // TODO List<?> ??
	public List<EIT_Input_HashSourceFile_Triple> x;

	public EOT_OutputFileImpl(final @NotNull List<EIT_Input> inputs, final @NotNull FileNameProvider filename,
							  final @NotNull EOT_OutputType type, final @NotNull EG_Statement sequence) {
		_filename = filename;
		_type = type;
		_sequence = sequence;
		_inputs.addAll(inputs);
	}

	public EOT_OutputFileImpl(final @NotNull List<EIT_Input> inputs, final @NotNull String filename,
							  final @NotNull EOT_OutputType type, final @NotNull EG_Statement sequence) {
		this(inputs, new DefaultFileNameProvider(filename), type, sequence);
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
		return "(%s) '%s'".formatted(_type, _filename);
	}

	// rules/constraints whatever
}
