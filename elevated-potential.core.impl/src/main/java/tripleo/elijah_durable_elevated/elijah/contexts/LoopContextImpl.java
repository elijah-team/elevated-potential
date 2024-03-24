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
package tripleo.elijah_durable_elevated.elijah.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.ILoopContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.elijah.lang.impl.*;
import tripleo.elijah_durable_elevated.elijah.util.ProgramIsLikelyWrong;

/**
 * Created Mar 26, 2020 at 9:40:43 PM
 * <p>
 * @author Tripleo
 */
public class LoopContextImpl extends ContextImpl implements ILoopContext {
	private final Context _parent;
	private final Loop carrier;

	public LoopContextImpl(final Context cur, final Loop loop) {
		carrier = loop;
		_parent = cur;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final @NotNull String name, final int level, final @NotNull LookupResultList Result,
								   final @NotNull ISearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());

		if (carrier.getIterNameToken() != null) {
			if (carrier.getIterName() != null) {
				if (name.equals(carrier.getIterName())) { // reversed to prevent NPEs
					final IdentExpression ie = carrier.getIterNameToken();
					Result.add(name, level, ie, this);
				}
			}
		}

		for (final StatementItem item : carrier.getItems()) {
			if (!(item instanceof ClassStatement) && !(item instanceof NamespaceStatement)
					&& !(item instanceof FunctionDef) && !(item instanceof VariableSequenceImpl)
					&& !(item instanceof AliasStatementImpl))
				continue;
			if (item instanceof OS_NamedElement) {
				if (((OS_NamedElement) item).name().equals(name)) {
					Result.add(name, level, (OS_Element) item, this);
				}
			}
			if (item instanceof VariableSequenceImpl) {
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2("1102 " + item);
				for (final VariableStatement vs : ((VariableSequence) item).items()) {
					if (vs.getName().equals(name)) {
						Result.add(name, level, vs, this);
					}
				}
			}
		}

		if (carrier.getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one) {
				if (context != null) {
					context.lookup(name, level + 1, Result, alreadySearched, false); // TODO test this
				} else {
					throw new ProgramIsLikelyWrong();
				}
			}
		}

		return Result;
	}
}

//
//
//
