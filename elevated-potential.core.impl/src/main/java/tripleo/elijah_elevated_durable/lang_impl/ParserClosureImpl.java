/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah_elevated_durable.lang_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.comp.i.Compilation;

/*
 * Created on Sep 1, 2005 8:16:32 PM
 */
public class ParserClosureImpl extends ProgramClosureImpl implements tripleo.elijah.lang.i.ParserClosure {
	public final OS_Module module;

	public ParserClosureImpl(final String fn, @NotNull final Compilation compilation) {
		var c2 = (Compilation)compilation;
		module = c2.moduleBuilder().withFileName(fn).addToCompilation().build();
	}

	@Override
	public OS_Package defaultPackageName(final Qualident aPackageName) {
		return module.pushPackageNamed(aPackageName);
	}

	@Override
	public @NotNull OS_Module module() {
		return module;
	}
}
