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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.IFuncExprContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_elevateder.contexts.FuncExprContextImpl;

import java.util.List;

/**
 * @author Tripleo
 * <p>
 * Created Mar 30, 2020 at 7:41:52 AM
 */
public class FuncExprImpl extends BaseFunctionDef implements FuncExpr {

	public FuncExprImpl(FuncExprContextImpl _ctx, TypeName _returnType) {
		super();
		setReturnType(_returnType);
		setContext(_ctx);
		// TODO 10/15 should the below be in the above?
		_a = new AttachedImpl(_ctx);
	}

	private FuncExprContextImpl _ctx;
	// private Scope3 scope3;
	// private FormalArgList argList = new FormalArgListImpl();
	private TypeName            _returnType;

	@Deprecated
	public FuncExprImpl() {
		//throw new UnintendedUseException(); // ?? FIXME 10/18
	}

	@Override
	public @NotNull List<FormalArgListItem> falis() {
		return mFal.falis();
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	/****** FOR IEXPRESSION ******/
	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.FUNC_EXPR;
	}

//	public List<FunctionItem> getItems() {
//		List<FunctionItem> collection = new ArrayList<FunctionItem>();
//		for (OS_Element element : scope3.items()) {
//			if (element instanceof FunctionItem)
//				collection.add((FunctionItem) element);
//		}
//		return collection;
////		return items;
//	}

	@Override
	public @Nullable IExpression getLeft() {
		return null;
	}

	@Override
	public @Nullable OS_Element getParent() {
//		throw new NotImplementedException();
		return null; // getContext().getParent().carrier() except if it is an Expression; but
		// Expression is not an Element
	}

	public Scope3 getScope() {
		return scope3;
	}

	@Override
	public boolean is_simple() {
		return false;
	}

	@Override
	public void postConstruct() {
		// nop
	}

	@Override
	public @Nullable String repr_() {
		return null;
	}

	@Override
	public TypeName returnType() {
		return _returnType;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setArgList(FormalArgList argList) {
		mFal = argList;
	}

	@Override
	public void setContext(final IFuncExprContext ctx) {
		setContext((FuncExprContextImpl) ctx);
	}

	public void setArgs(final ExpressionList ael) {
		// mFal = new FormalArgListImpl();
		// for (IExpression iExpression : ael) {
		// mFal.next().s
		// }
	}

	public void setContext(final FuncExprContextImpl ctx) {
		_ctx = ctx;
	}

	@Override
	public void setHeader(final FunctionHeader aFunctionHeader) {
		throw new NotImplementedException();
	}

	@Override
	public void setKind(final ExpressionKind aExpressionKind) {
		throw new NotImplementedException();
	}

	@Override
	public void setLeft(final IExpression iexpression) {
		throw new NotImplementedException();
	}

	@Override
	public void setReturnType(final TypeName tn) {
		_returnType = tn;
	}

	/************* FOR THE OTHER ONE ******************/

	@Override
	public void type(final TypeModifiers modifier) {
		assert modifier == TypeModifiers.FUNCTION || modifier == TypeModifiers.PROCEDURE;
	}

//	@Override
//	public void scope(Scope3 sco) {
//		scope3 = sco;
//	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitFuncExpr(this);
	}

}

//
//
//
