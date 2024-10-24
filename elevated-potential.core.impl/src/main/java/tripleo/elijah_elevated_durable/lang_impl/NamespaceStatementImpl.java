/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.NamespaceContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_elevateder.contexts.NamespaceContextImpl;
import tripleo.elijah_elevateder.lang.types.OS_UserNamespaceType;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

/**
 * @author Tripleo(sb)
 * <p>
 * Created Apr 2, 2019 at 11:08:12 AM
 */
public class NamespaceStatementImpl extends _CommonNC implements Documentable, ClassItem, NamespaceStatement {

	private final OS_Element           parent;
	private       NamespaceTypes       _kind;
	private       OS_UserNamespaceType __cached_osType;

	public NamespaceStatementImpl(final OS_Element aElement, final Context context) {
		parent = aElement; // setParent
		if (aElement instanceof OS_Module) {
			final OS_Module module = (OS_Module) aElement;
			//
			this.setPackageName(module.pullPackageName());
			_packageName.addElement(this);
			module.add(this);
		} else if (aElement instanceof OS_Container) {
			((OS_Container) aElement).add(this);
		} else {
			throw new IllegalStateException(String.format("Cant add NamespaceStatement to %s", aElement));
		}
		setContext(new NamespaceContextImpl(context, this));
	}

	@Override
	public OS_Type getOS_Type() {
		if (__cached_osType == null) {
			__cached_osType = new OS_UserNamespaceType(this);
		}
		return __cached_osType;

	}

	@Override // OS_Container
	public void add(final OS_Element anElement) {
		if (anElement instanceof ClassItem) {
			items.add((ClassItem) anElement);
		} else {
			final String s = String.format("[NamespaceStatement#add] not a ClassItem: %s", anElement);
			SimplePrintLoggerToRemoveSoon.println_err_2(s);
		}
	}

	@Override
	public @NotNull FunctionDef funcDef() {
		return new FunctionDefImpl(this, getContext());
	}

	@Override // OS_Element
	public Context getContext() {
		return _a.getContext();
	}

	@Override
	public NamespaceTypes getKind() {
		if (_kind == null && name().equals("__MODULE__"))
			return NamespaceTypes.MODULE; // semi HACK

		return _kind;
	}

	@Override // OS_Element
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public IInvariantStatement invariantStatement() {
		throw new NotImplementedException();
	}

	@Override
	public void postConstruct() {
		if (nameToken == null || nameToken.getText().equals("")) {
			setType(NamespaceTypes.MODULE);
		} else if (nameToken.getText().equals("_")) {
			setType(NamespaceTypes.PRIVATE);
		} else if (nameToken.getText().equals("__package__")) {
			setType(NamespaceTypes.PACKAGE);
		} else {
			setType(NamespaceTypes.NAMED);
		}
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void setCategory(El_Category aCategory) {
		// TODO Auto-generated method stub

	}

//	@Override
	public void setContext(final NamespaceContext ctx) {
		_a = new AttachedImpl(ctx);
	}

	@Override
	public void setType(final NamespaceTypes aType) {
		_kind = aType;
	}

	@Override
	public @NotNull StatementClosure statementClosure() {
		return new AbstractStatementClosure(new AbstractScope2(this) {
			@Override
			public void add(final StatementItem aItem) {
				NamespaceStatementImpl.this.add((OS_Element) aItem);
			}

			@Override
			public StatementClosure statementClosure() {
				throw new NotImplementedException();
//				return null;
			}

			@Override
			public void statementWrapper(final IExpression aExpr) {
				throw new NotImplementedException();
			}

		});
	}

	@Override
	public String toString() {
		return String.format("<Namespace %s `%s'>", getPackageName().getName(), getName());
	}

	@Override
	public TypeAliasStatement typeAlias() {
		throw new NotImplementedException();
	}

	@Override // OS_Element
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitNamespaceStatement(this);
	}

	@Override
	public @NotNull ProgramClosure XXX() {
		return new ProgramClosureImpl() {
		};
	}

	// region ClassItem

	// endregion

}

//
//
//
