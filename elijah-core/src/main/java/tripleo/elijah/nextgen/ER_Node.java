package tripleo.elijah.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.graph.i.CE_Path;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.nextgen.outputstatement.*;

import java.nio.file.*;

/**
 * See
 * {@link CompOutput#writeToPath(CE_Path, EG_Statement)}
 */
// TODO 09/04 Duplication madness
public interface ER_Node {
	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull ER_Node of(@NotNull CP_Path p, @NotNull EG_Statement seq) {
		return new ER_Node() {
			@Override
			public Path getPath() {
				Path pp = p.getPath();
				return pp;
			}

			@Override
			public EG_Statement getStatement() {
				return seq;
			}

			@Override
			public @NotNull String toString() {
				return "17 ER_Node " + p.toFile();
			}
		};
	}

	Path getPath();

	EG_Statement getStatement();
}
