/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 *
 */
package tripleo.elijah_elevateder.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.NamespaceContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.*;


/**
 * @author Tripleo
 *         <p>
 *         Created Mar 29, 2020 at 8:59:42 PM
 */
public class NamespaceContextImpl extends ContextImpl implements NamespaceContext {

	private final Context _parent;
	public NamespaceStatement carrier;

	public NamespaceContextImpl(NamespaceStatement namespaceStatement) {
		_parent = null;
		carrier = namespaceStatement;
	}

	public NamespaceContextImpl(final Context aParent, final NamespaceStatement ns) {
		_parent = aParent;
		carrier = ns;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name,
								   final int level,
								   final @NotNull LookupResultList Result,
								   final @NotNull ISearchList alreadySearched,
								   final boolean one) {
		alreadySearched.add(carrier.getContext());

		for (final ClassItem item : carrier.getItems()) {
			if (!(item instanceof ClassStatement) && !(item instanceof NamespaceStatement)
					&& !(item instanceof VariableSequenceImpl) && !(item instanceof AliasStatementImpl)
					&& !(item instanceof FunctionDef) && !(item instanceof PropertyStatement))
				continue;
			if (item instanceof OS_NamedElement) {
				if (((OS_NamedElement) item).name().equals(name)) {
					Result.add(name, level, item, this);
				}
			}
			if (item instanceof VariableSequenceImpl) {
//				tripleo.elijah.util.Stupidity.println_out_2("[NamespaceContext#lookup] VariableSequenceImpl "+item);
				for (final VariableStatement vs : ((VariableSequenceImpl) item).items()) {
					if (vs.getName().equals(name))
						Result.add(name, level, vs, this);
				}
			}
		}

		if (getParent() != null) {
			final Context context = getParent();

			if (!alreadySearched.contains(context) || !one) {
				return context.lookup(name, level + 1, Result, alreadySearched, false);
			}
		}

		return Result;
	}
}
