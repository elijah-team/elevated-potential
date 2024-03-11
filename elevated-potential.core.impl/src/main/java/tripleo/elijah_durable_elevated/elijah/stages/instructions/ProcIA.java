/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.stages.instructions;

import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.ProcTableEntry;

import org.jetbrains.annotations.NotNull;

/**
 * Created 1/12/21 4:22 AM
 */
public class ProcIA implements InstructionArgument {
	private final int             index;
	private final BaseEvaFunction generatedFunction;

	public ProcIA(final int aIndex, final BaseEvaFunction aGeneratedFunction) {
		index             = aIndex;
		generatedFunction = aGeneratedFunction;
	}


	@Override
	public @NotNull String toString() {
		return "ProcIA{" + "index=" + index + ", " + "func=" + getEntry() + '}';
	}

	public @NotNull ProcTableEntry getEntry() {
		return generatedFunction.getProcTableEntry(index);
	}

	public int index() {
		return index;
	}

	public BaseEvaFunction generatedFunction() {
		return generatedFunction;
	}
}

//
//
//
