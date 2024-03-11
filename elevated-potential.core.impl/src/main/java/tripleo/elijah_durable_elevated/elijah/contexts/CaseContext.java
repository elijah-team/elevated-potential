/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.contexts;

import org.jetbrains.annotations.*;

import tripleo.elijah_durable_elevated.elijah.DebugFlags;
import tripleo.elijah.contexts.ICaseContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.*;
import tripleo.elijah_durable_elevated.elijah.lang.impl.ContextImpl;

/**
 * Created 9/24/20 6:11 PM
 */
public class CaseContext extends ContextImpl implements ICaseContext {
	private final Context         _parent;
	private final CaseConditional carrier;

	public CaseContext(final Context aParent, final CaseConditional mc) {
		this._parent = aParent;
		this.carrier = mc;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name,
								   final int level,
								   final LookupResultList Result,
								   final @NotNull ISearchList alreadySearched,
								   final boolean one) {
		alreadySearched.add(carrier.getContext());

		if (DebugFlags.FORCE_IGNORE) {
			final String iterName = carrier.getIterName();
			if (iterName != null) {
				if (name.equals(iterName)) { // reversed to prevent NPEs IdentExpression ie =
					carrier.getIterNameToken();
					Result.add(name, level, carrier, this);
				}
			}
		}
		if (DebugFlags.FORCE_IGNORE) {
			if (carrier.getParent() != null) {
				final Context context = getParent();
				if (!alreadySearched.contains(context) || !one) context.lookup(name, level + 1, Result, alreadySearched, false); // TODO test this } return Result;
			}
		}

		throw new NotImplementedException(); // carrier.singleidentcontext
	}
}

//
//
//
