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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.IFuncExprContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.ContextImpl;
import tripleo.elijah_elevated_durable.lang_impl.VariableSequenceImpl;


/**
 * Created 8/21/20 11:53 PM
 */
public class FuncExprContextImpl extends ContextImpl implements IFuncExprContext {
	private final Context _parent;
	private final FuncExpr carrier;

	public FuncExprContextImpl(final Context cur, final FuncExpr pc) {
		_parent = cur;
		carrier = pc;
	}

	@Override
	public @Nullable Context getParent() {
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
				for (final VariableStatement vs : ((VariableSequenceImpl) item).items()) {
					if (vs.getName().equals(name))
						Result.add(name, level, vs, this);
				}
			}
		}
		for (final FormalArgListItem arg : carrier.falis()) {
			if (arg.name().equals(name)) {
				Result.add(name, level, arg, this);
			}
		}
		if (carrier.getParent() != null) {
			final Context context = getParent()/* carrier.getParent().getContext() */;
			if (!alreadySearched.contains(context) || !one)
				return context.lookup(name, level + 1, Result, alreadySearched, false);
		}
		return Result;
	}

	@Override
	public @NotNull OS_Module module() {
		return null;
	}

}

//
//
//
