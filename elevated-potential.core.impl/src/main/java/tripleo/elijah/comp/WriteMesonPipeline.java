/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.g.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.functionality.f292.F292_WriteRoot;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.io.*;
import tripleo.elijah_prolific.v.V;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;

import static tripleo.elijah.util.Helpers.*;

/**
 * Created 9/13/21 11:58 PM
 */
public class WriteMesonPipeline extends PipelineMember implements @NotNull Consumer<Supplier<Old_GenerateResult>>, GPipelineMember {
	final                  Pattern         pullPat = Pattern.compile("/[^/]+/(.+)");
	private final @NotNull Compilation     c;
	private final @NotNull IPipelineAccess pa;
	private final          WritePipeline   writePipeline;
	@NotNull
	DoubleLatch<Multimap<CompilerInstructions, String>> write_makefiles_latch = new DoubleLatch<>(
			this::write_makefiles_action);
	private Consumer<Multimap<CompilerInstructions, String>> _wmc;
	private Supplier<Old_GenerateResult>                     grs;

	public WriteMesonPipeline(final @NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;

		final AccessBus     ab             = pa.getAccessBus();
		final Compilation   compilation    = ab.getCompilation();
		final WritePipeline WritePipeline1 = ab.getPipelineAccess().getWitePipeline();

		c             = compilation;
		writePipeline = WritePipeline1;
	}

	@Override
	public void accept(final @NotNull Supplier<Old_GenerateResult> aGenerateResultSupplier) {
		final GenerateResult gr = aGenerateResultSupplier.get();
		// 08/13 tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("WMP66 "+gr);
		grs = aGenerateResultSupplier;
		int y = 2;
	}

	public @NotNull Consumer<Supplier<Old_GenerateResult>> consumer() {
		return new Consumer<Supplier<Old_GenerateResult>>() {
			@Override
			public void accept(final Supplier<Old_GenerateResult> aGenerateResultSupplier) {
				if (grs != null) {
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2("234 grs not null " + grs.getClass().getName());
					return;
				}

				assert false;
				grs = aGenerateResultSupplier;
				// final GenerateResult gr = aGenerateResultSupplier.get();
			}
		};
	}

	@Nullable
	String pullFileName(@NotNull String aFilename) {
		// return aFilename.substring(aFilename.lastIndexOf('/')+1);
		Matcher x = pullPat.matcher(aFilename);
		try {
			if (x.matches())
				return x.group(1);
		} catch (IllegalStateException ignored) {
		}
		return null;
	}

	@Override
	public void run(final Ok aSt, final CB_Output aOutput) throws Exception {
		write_makefiles();
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}

	private void write_makefiles() {
		Multimap<CompilerInstructions, String> lsp_outputs = writePipeline.getSt().lsp_outputs; // TODO move this
		write_makefiles_consumer().accept(lsp_outputs);

		// write_makefiles_latch.notify(lsp_outputs);
		write_makefiles_latch.notifyLatch(Ok.instance());
	}

	public @NotNull Consumer<Multimap<CompilerInstructions, String>> write_makefiles_consumer() {
		if (_wmc != null)
			return _wmc;

		_wmc = write_makefiles_latch::notifyData;

		return _wmc;
	}

	private void write_makefiles_action(final @NotNull Multimap<CompilerInstructions, String> lsp_outputs) {
		List<String> dep_dirs = new LinkedList<String>();

		try {
			write_root(lsp_outputs, dep_dirs);

			for (final CompilerInstructions compilerInstructions : lsp_outputs.keySet()) {
				final String sub_dir = compilerInstructions.getName();
				// final Path dpath = getPath2(sub_dir);

				getPath2(sub_dir).getPathPromise().then(dpath -> {
					if (dpath.toFile().exists()) {
						try {
							write_lsp(lsp_outputs, compilerInstructions, sub_dir);
						} catch (IOException aE) {
							throw new RuntimeException(aE);
						}
					}
				});
			}

			write_prelude();
		} catch (IOException aE) {
			throw new RuntimeException(aE);
		}
	}

	private void write_root(@NotNull Multimap<CompilerInstructions, String> lsp_outputs,
							@NotNull List<String> aDep_dirs) throws IOException {
		final CP_Path path2_ = getPath2("meson.build");

		path2_.getPathPromise().then(path2 -> {
			CharSink root_file = null;
			try {
				root_file = c.getIO().openWrite(path2);
			} catch (IOException aE) {
				throw new RuntimeException(aE);
			}
			try {
				String project_name = c.getProjectName();
				F292_WriteRoot f292 = new F292_WriteRoot(project_name);
				f292.write_root(lsp_outputs, aDep_dirs, path2, root_file, path2_);
			} finally {
				((FileCharSink) root_file).close();
			}
		});
	}

	private CP_Path getPath2(final String aName) {
		var root = pa.getCompilation().paths().outputRoot();

		var child = root.child(aName);
		return child;
	}

	private void write_lsp(@NotNull Multimap<CompilerInstructions, String> lsp_outputs,
						   CompilerInstructions compilerInstructions, String aSub_dir) throws IOException {
		if (true || false) {
			CP_Path path = getPath2(aSub_dir, "meson.build");
			path.getPathPromise().then(pp -> {
				final MesonFile mesonFile = new MesonFile(this, aSub_dir, lsp_outputs, compilerInstructions, path);

				@NotNull final EG_Statement stmt = mesonFile;

				mesonFile.getPath().getPathPromise().then(ppp -> {

					// final String pathString = mesonFile.getPathString();
					final String pathString2 = ppp.toString();

					final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), pathString2, EOT_OutputType.BUILD, stmt);
					c.getOutputTree().add(off);
				});

			});
		}
	}

	private void write_prelude() throws IOException {
		if (true || false) {
			final CP_Path ppath1 = c.paths().outputRoot().child("Prelude");
			final CP_Path ppath  = ppath1.child("meson.build");

			if (false) {
				ppath1.getPathPromise().then(pp -> {
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("mkdirs 215 " + ppath1.toFile());
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("mkdirs 215b " + pp.toFile());
					pp.toFile().mkdirs(); // README just in case -- but should be unnecessary at this point
				});
			}

			List<String> files = List_of("'Prelude.c'");

			final StringBuilder sb = new StringBuilder();

			sb.append(String.format("Prelude_sources = files(\n%s\n)", String_join("\n", files)));
			sb.append("\n");
			sb.append("Prelude = static_library('Prelude', Prelude_sources, install: false,)"); // include_directories,
			// dependencies: [],
			sb.append("\n");
			sb.append("\n");
			sb.append(String.format("%s_dep = declare_dependency( link_with: %s )", "Prelude", "Prelude")); // include_directories
			sb.append("\n");

			@NotNull final EG_Statement stmt = EG_Statement.of(sb.toString(), EX_Explanation.withMessage("WriteMesonPipeline"));
			final String         s   = ppath.toString();

			V.asv(V.e.WMP_write_prelude, ""+ppath);

			final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), s, EOT_OutputType.BUILD, stmt);
			c.getOutputTree().add(off);
		}

	}

	private CP_Path getPath2(final String aName, final String aName2) {
		var root = pa.getCompilation().paths().outputRoot();

		var child = root.child(aName).child(aName2);
		return child;
	}
}

//
//
//
