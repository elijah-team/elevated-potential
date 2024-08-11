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
import tripleo.elijah.util.*;

/**
 * Created 2/28/21 3:23 AM
 */
public interface EvaContainer extends EvaNode {

	OS_Element getElement();

	@NotNull
	Maybe<VarTableEntry> getVariable(String aVarName);
}

//
//
//
