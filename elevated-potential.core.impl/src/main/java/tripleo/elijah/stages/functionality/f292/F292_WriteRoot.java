package tripleo.elijah.stages.functionality.f292;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.util.io.CharSink;
import tripleo.elijah_prolific.v.V;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.String_join;

public class F292_WriteRoot {
	private final String project_name;

	public F292_WriteRoot(final String aProjectName) {
		project_name = aProjectName;
	}

	public void write_root(final @NotNull Multimap<CompilerInstructions, String> lsp_outputs,
						   final @NotNull List<String> aDep_dirs,
						   final Path path2,
						   final CharSink root_file,
						   final CP_Path path2_) {
		String project_string = String
				.format("project('%s', 'c', version: '1.0.0', meson_version: '>= 0.48.0',)", project_name);
		root_file.accept(project_string);
		root_file.accept("\n");

		for (CompilerInstructions compilerInstructions : lsp_outputs.keySet()) {
			String name = compilerInstructions.getName();
			// final Path dpath = getPath2(name);

			final CharSink finalRoot_file = root_file;
			path2_.child(name).getPathPromise().then(dpath -> {
				if (dpath.toFile().exists()) {
					String name_subdir_string = String.format("subdir('%s')\n", name);
					finalRoot_file.accept(name_subdir_string);
					aDep_dirs.add(name);
				}
			});
		}
		aDep_dirs.add("Prelude");
//			String prelude_string = String.format("subdir(\"Prelude_%s\")\n", /*c.defaultGenLang()*/"c");
		String prelude_string = "subdir('Prelude')\n";
		root_file.accept(prelude_string);

//			root_file.accept("\n");

		String deps_names = String_join(", ", aDep_dirs.stream().map(x -> String.format("%s", x)) // TODO _lib
				// ??
				.collect(Collectors.toList()));
		root_file.accept(String.format("%s_bin = executable('%s', link_with: [ %s ], install: true)",
									   project_name, project_name, deps_names)); // dependencies, include_directories

		V.asv(V.e.WMP_write_root, ""+ path2);
	}
}
