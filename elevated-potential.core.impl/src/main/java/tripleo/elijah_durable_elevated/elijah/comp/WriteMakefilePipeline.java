package tripleo.elijah_durable_elevated.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.nextgen.outputtree.EOT_OutputFileImpl;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.Old_GenerateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;

public class WriteMakefilePipeline extends PipelineMember implements Consumer<Supplier<Old_GenerateResult>> {
	private final IPipelineAccess pa;

	public WriteMakefilePipeline(final @NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
	}

	@Override
	public void accept(Supplier<Old_GenerateResult> t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(Ok st, CB_Output aOutput) throws Exception {
		final Compilation    c   = pa.getCompilation();
		final EOT_OutputTree cot = c.getOutputTree();

		cot.recompute();

		final List<EOT_OutputFile> list1 = cot.getList();
		final EOT_OutputFile       eof   = testable_part(list1);

		cot.add(eof);
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}

	@NotNull
	EOT_OutputFile testable_part(final @NotNull List<EOT_OutputFile> list1) {
		var list = list1.stream().filter(off -> off.getType() == EOT_OutputType.SOURCES).collect(Collectors.toList());

		var sb = new StringBuilder();

		sb.append("CC=cc -g\n");
		sb.append("CODE=code2/\n");
		sb.append("\n");
		sb.append("all:\n");
//		sb.append("all:\n\tfalse");

		// sb.append(Helpers.String_join("\n",
		// list.stream().map(x->x.getFilename()).collect(Collectors.toList())));

		final List<EOT_OutputFile> prelc   = new ArrayList<>();
		final List<EOT_OutputFile> stdlibc = new ArrayList<>();
		final List<EOT_OutputFile> sourcec = new ArrayList<>();
		final List<EOT_OutputFile> otherc  = new ArrayList<>();

		for (EOT_OutputFile off : list) {
			var fn = off.getFilename();

			if (fn.startsWith("/Prelude/")) {
				prelc.add(off);
			} else if (fn.startsWith("/stdlib/")) {
				stdlibc.add(off);
			} else {// if (fn.startsWith("/stdlib/")) {
				sourcec.add(off);
			}
		}

		var g = new G();
		process(g, prelc);
		process(g, stdlibc);
		process(g, sourcec);
		process1(g);

		sb.append('\n');
		sb.append(g.getText());
		sb.append('\n');

		var seq = EG_Statement.of(sb.toString(), EX_Explanation.withMessage("the Makefile"));

		/*
		 * var input_list = list1.stream() .map(off -> off.getFilename()) .map(s -> new
		 * EIT_ModuleInput(string_to_module(s), null)) .collect(Collectors.toList());
		 */

		final EOT_OutputFile eof = new EOT_OutputFileImpl(List_of(), "Makefile", EOT_OutputType.BUILD, seq);
		return eof;
	}

	private void process(final @NotNull G aG, final @NotNull List<EOT_OutputFile> aL) {
		final StringBuilder sb = new StringBuilder();

		for (EOT_OutputFile off : aL) {
			var fn = off.getFilename();

			if (fn.endsWith(".c")) {
				final String[]     fn2a = fn.split("/");
				final List<String> fn2  = List.of(fn2a);

				SimplePrintLoggerToRemoveSoon.println_out_4(115, ""+fn2);

				final List<String> fn3 = fn2.subList(1, fn2.size() - 1);
				final String       fn4 = Helpers.String_join("/", fn3);

				sb.append("\t-mkdir -p \"B/%s\"\n".formatted(fn4));

				final String fn_dot_o = fn.substring(0, fn.length() - 2) + ".o";
				aG.add_object(fn_dot_o);
				sb.append("\t$(CC) -c $(CODE)/%s -o B/%s -I$(CODE)\n".formatted(fn, fn_dot_o));
			}
		}

		aG.append(sb.toString());
	}

	private void process1(final @NotNull G aG) {
		aG.append_string("\tcc \\\n");
		for (String object : aG.objects) {
			aG.append_string("\t\tB/%s \\\n".formatted(object));
		}
		aG.append_string("\t\t-o B/output.exe -l gc\n");
		aG.append_string("\n");
	}

	class G {
		final StringBuilder sb      = new StringBuilder();
		final List<String>  objects = new ArrayList<>();

		public void add_object(final String obj) {
			objects.add(obj);
		}

		public void append(final String aString) {
			sb.append(aString);
		}

		public void append_string(final String s) {
			sb.append(s);
		}

		public @NotNull String getText() {
			return sb.toString();
		}
	}
}
