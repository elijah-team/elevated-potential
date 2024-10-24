/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.IWithContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.ContextImpl;
import tripleo.elijah_elevated_durable.lang_impl.VariableSequenceImpl;


/**
 * Created 8/30/20 1:42 PM
 */
public class WithContext extends ContextImpl implements IWithContext {

	private final Context _parent;
	private final WithStatement carrier;

	public WithContext(final WithStatement carrier, final Context _parent) {
		this.carrier = carrier;
		this._parent = _parent;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final @NotNull LookupResultList Result,
								   final @NotNull ISearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());

		for (final FunctionItem item : carrier.getItems()) {
			if (!(item instanceof ClassStatement) && !(item instanceof NamespaceStatement)
					&& !(item instanceof FunctionDef) && !(item instanceof VariableSequenceImpl))
				continue;
			if (item instanceof OS_NamedElement) {
				if (((OS_NamedElement) item).name().equals(name)) {
					Result.add(name, level, item, this);
				}
			} else if (item instanceof VariableSequenceImpl) {
//				tripleo.elijah.util.Stupidity.println_out_2("[FunctionContext#lookup] VariableSequenceImpl "+item);
				for (final VariableStatement vs : ((VariableSequence) item).items()) {
					if (vs.getName().equals(name))
						Result.add(name, level, vs, this);
				}
			}
			// TODO search hidden
		}

		if (carrier.getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one) {
				assert context != null;
				context.lookup(name, level + 1, Result, alreadySearched, false); // TODO test this
			}
		}
		return Result;

	}

}

//
//
//
