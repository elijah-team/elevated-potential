package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;
import tripleo.util.buffer.*;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.nextgen.inputtree.EIT_Input_HashSourceFile_Triple;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;
import tripleo.util.buffer.DefaultBuffer;
import tripleo.util.buffer.TextBuffer;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class WPIS_WriteInputs implements WP_Individual_Step {
	private final Map<String, Operation<String>> ops = new HashMap<>();

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final WP_State_Control sc) {
		var root = st.c.paths().outputRoot();
		var fn1 = root.child("inputs.txt");

		final DefaultBuffer buf = new DefaultBuffer("");

		final List<IO_._IO_ReadFile> recordedreads = st.c.getIO().recordedreads_io();

		//for (final IO._IO_ReadFile readFile : recordedreads) {
		//	final String fn = readFile.getFileName();
		//
		//	final Operation<String> op = append_hash(buf, readFile);
		//
		//	ops.put(fn, op);
		//
		//	if (op.mode() == Mode.FAILURE) {
		//		break;
		//	}
		//}

		String s = buf.getText();

		final @NotNull EOT_OutputTree ot = st.c.getOutputTree();

		final List<EIT_Input_HashSourceFile_Triple> yys = new ArrayList<>();

		{
			for (final IO_._IO_ReadFile file : recordedreads) {
				var decoded = EIT_Input_HashSourceFile_Triple.decode(file);
				yys.add(decoded);

				ops.put(decoded.filename(), Operation.success(decoded.hash())); // FIXME extract actual operation
			}
		}

		final EG_SequenceStatement seq = new EG_SequenceStatement(
				new EG_Naming("<<WPIS_WriteInputs>>"),
				List_of(EG_Statement.of(s, () -> "<<WPIS_WriteInputs>> >> statement")));

		fn1.getPathPromise().then(pp -> {
			String string = "inputs.txt";// pp.toFile().toString(); //fn1.getPath().toFile().toString();

			final EOT_OutputFileImpl off = new EOT_OutputFileImpl(List_of(),
																  new _U_OF.DefaultFileNameProvider(string),
																  EOT_OutputType.INPUTS,
																  seq);

			off.x = yys;

			ot.add(off);
		});
	}

	public @NotNull Operation<String> append_hash(@NotNull TextBuffer outputBuffer, @NotNull String aFilename) {
		final @NotNull Operation<String> hh2 = Helpers.getHashForFilename(aFilename);

		if (hh2.mode() == Mode.SUCCESS) {
			final String hh = hh2.success();

			assert hh != null;

			// TODO EG_Statement here

			outputBuffer.append(hh);
			outputBuffer.append(" ");
			outputBuffer.append_ln(aFilename);
		}

		return hh2;
	}

	public @NotNull Operation<String> append_hash(@NotNull TextBuffer outputBuffer,
			@NotNull IO_._IO_ReadFile aReadFile) {
		final @NotNull Operation<String> hh2 = aReadFile.hash();

		if (hh2.mode() == Mode.SUCCESS) {
			final String hh = hh2.success();

			assert hh != null;

			// TODO EG_Statement here

			outputBuffer.append(hh);
			outputBuffer.append(" ");
			outputBuffer.append_ln(aReadFile.getFileName());
		}

		return hh2;
	}

	//@Override
	public Operation<Ok> execute(final CK_Monitor aMonitor) {
		return null;
	}
}
