/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah_elevated_durable.lang_impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_fluffy.util.UnintendedUseException;
import tripleo.elijah_elevateder.contexts.FunctionContextImpl;

import java.util.ArrayList;
import java.util.List;

/*
 * Created on Aug 30, 2005 8:43:27 PM
 */
public class DefFunctionDefImpl extends BaseFunctionDef implements tripleo.elijah.lang.i.DefFunctionDef {

	private final          OS_Element         parent;
	private final @NotNull List<FunctionItem> _items      = new ArrayList<FunctionItem>();
	private                IExpression        _expr;
	private @Nullable      TypeName           _returnType = null;

	public DefFunctionDefImpl(OS_Element aElement, Context aContext) {
		parent = aElement;
		if (aElement instanceof OS_Container) {
			((OS_Container) parent).add(this);
		} else if (aElement instanceof PropertyStatement) {
			// do nothing
		} else {
			throw new IllegalStateException("adding DefFunctionDef to " + aElement.getClass().getName());
		}
		_a = new AttachedImpl(new FunctionContextImpl(aContext, this));
		setSpecies(Species.DEF_FUN);
	}

	@Override
	public void add(final FunctionItem seq) {
		_items.add(seq);
	}
//	private FormalArgList fal;

	@Override
	public @NotNull List<FunctionItem> getItems() {
		return _items; // TODO what about scope?
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	/**
	 * see {@link #_expr} for why getItems().size should be 0, or
	 */
	@Override
	public void postConstruct() {
//		super.postConstruct();
		if (getItems().size() != 1) {
			throw new IllegalStateException("Too many items"); // TODO convert to diagnostic?
		}
	}

	/**
	 * Can be {@code null} under the following circumstances:<br/>
	 * <br/>
	 * <p>
	 * 1. The compiler(parser) didn't get a chance to set it yet<br/>
	 * 2. The programmer did not specify a return value and the compiler must deduce
	 * it<br/>
	 * 3. The function is a void-type and specification isn't required <br/>
	 *
	 * @return the associated TypeName or NULL
	 */
	@Override
	public @Nullable TypeName returnType() {
		return _returnType;
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		sw.fieldIdent("name", getNameNode()); // TODO 10/15 think about this later
	}

	@Override
	public void set(FunctionModifiers mod) {
		// TODO Auto-generated method stub
		throw new NotImplementedException(); // TODO think about this later
	}

	@Override
	public void setAbstract(boolean b) {
		// README defs cant be abstract
		throw new UnintendedUseException();
	}

	@Override
	public void setBody(FunctionBody aFunctionBody) {
		throw new UnintendedUseException(); // README defs cant have body
	}

	@Override
	public void setCategory(El_Category aCategory) {
		throw new NotImplementedException(); // TODO 10/15 implement me
	}

	@Override
	public void setHeader(@NotNull FunctionHeader aFunctionHeader) {
		setFal(aFunctionHeader.getFal());
//		set(aFunctionHeader.getModifier()); // TODO
		setName(aFunctionHeader.getName());
		setReturnType(aFunctionHeader.getReturnType());
	}

	@Override
	public void setReturnType(final TypeName tn) {
		this._returnType = tn;
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitDefFunction(this);
	}

	@Override
	public void setBody(IExpression aExpression) {
		setExpr(aExpression);
	}

	// wont use parent scope.items.add so this is ok
	@Override
	public void setExpr(final IExpression aExpr) {
		_expr = aExpr;
		_items.add(new StatementWrapperImpl(_expr, getContext(), this));
	}

}

//
//
//
