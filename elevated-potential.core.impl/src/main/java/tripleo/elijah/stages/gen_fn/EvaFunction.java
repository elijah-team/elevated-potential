/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.nextgen.*;

import java.util.*;

/**
 * Created 6/27/21 9:40 AM
 */
public class EvaFunction extends BaseEvaFunction {
	private final @Nullable FunctionDef fd;

	public @NotNull Set<DR_Item> _idents = new HashSet<>();

	public EvaFunction(final @Nullable FunctionDef functionDef) {
		if (functionDef == null) {
			throw new AssertionError();
		}
		fd = functionDef;
	}

	// FIXME Eventual??
	@Override
	public @NotNull FunctionDef getFD() {
		if (fd == null) {
			throw new IllegalStateException("No function");
		} else {
			return fd;
		}
	}

	@Override
	public @Nullable VariableTableEntry getSelf() {
		if (getFD().getParent() instanceof ClassStatement) {
			return getVarTableEntry(0);
		} else {
			return null;
		}
	}

	@Override
	public String identityString() {
		return String.valueOf(fd);
	}

	@Override
	public @NotNull OS_Module module() {
		return getFD().getContext().module();
	}

	public String name() {
		return getFD().name();
	}

	@Override
	public String toString() {
		if (fd == null) {
			return "<EvaFunction {{unattached}}>";
		} else {
			final @NotNull Collection<FormalArgListItem> args   = fd.getArgs();
			final @Nullable OS_Element                    parent = fd.getParent();
			final @NotNull String                        name   = fd.name();

			//noinspection SpellCheckingInspection
			String pparent;

			if (parent == null) {
				pparent = "{{null parent}}";
			} else {
				pparent = parent.toString();
			}

			final String pte_string = args.toString(); // TODO wanted PTE.getLoggingString

			return String.format("<EvaFunction %s %s %s>", pparent, name, pte_string);
		}
	}

	//public FunctionDef fd() {
	//	return fd;
	//}
}

//
//
//
