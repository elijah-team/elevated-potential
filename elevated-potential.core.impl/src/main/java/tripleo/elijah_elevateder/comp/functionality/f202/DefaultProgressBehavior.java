/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.comp.functionality.f202;

import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_prolific.v.V;

/**
 * Created 8/11/21 6:01 AM
 */
public class DefaultProgressBehavior implements ProgressBehavior {
	@Override
	public void reportProgress(String a) {
		V.asv(V.e.f202_writing_logs, a);
		SimplePrintLoggerToRemoveSoon.println_out_4("202 Writing " + a);
	}
}

//
//
//
