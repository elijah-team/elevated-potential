/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.parser;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.ParserClosure;
import tripleo.elijah_elevated_durable.lang_impl.ParserClosureImpl;
import tripleo.elijah_elevateder.comp.i.Compilation;


public class Out {
	private final @NotNull ParserClosure pc;

	public Out(final String fn, final @NotNull Compilation aCompilation) {
		pc = new ParserClosureImpl(fn, aCompilation);
	}

	public @NotNull ParserClosure closure() {
		return pc;
	}

	@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NM_METHOD_NAMING_CONVENTION")
	public void FinishModule() {
		pc.module().finish();
	}

	public @NotNull OS_Module module() {
		return pc.module();
	}
}

//
//
//
