package tripleo.elijah.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.nextgen.outputstatement.*;

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
			public CP_Path getPath() {
//				Path pp = p.getPath();
				return p;
//				throw new NotImplementedException();
			}

			@Override
			public EG_Statement getStatement() {
				return seq;
			}

			@Override
			public @NotNull String toString() {
				return "17 ER_Node " + p.toString();
			}
		};
	}

	CP_Path getPath();

	EG_Statement getStatement();
}
