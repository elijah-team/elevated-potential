/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.lang.impl;

import tripleo.elijah.lang.i.Attached;
import tripleo.elijah.lang.i.Context;

/**
 * Created on 5/19/2019 at 02:09
 */
// TODO 10/15 this is everywhere. investigate for something else
public class AttachedImpl implements Attached {
	Context _context;

	public AttachedImpl(final Context aContext) {
		_context = aContext;
	}

	@Override
	public Context getContext() {
		return _context;
	}

	@Override
	public void setContext(final Context aContext) {
		_context = aContext;
	}
}

//
//
//
