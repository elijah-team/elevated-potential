/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface ImportStatement extends ModuleItem, ClassItem, StatementItem {
	List<Qualident> parts();

	//@Override
	//default void serializeTo(SmallWriter sw) {
	//
	//}

	void setAccess(AccessNotation aNotation);

	void setContext(IImportContext ctx);

	@Override
	default void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitImportStatment(this);
	}
}

//
//
//
