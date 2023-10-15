/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.util.*;

import java.util.*;

/**
 * @author Tripleo
 *         <p>
 *         Created Mar 30, 2020 at 7:41:52 AM
 */
public class FuncExprImpl extends BaseFunctionDef implements FuncExpr {

	private FuncExprContext _ctx;
	// private Scope3 scope3;
	// private FormalArgList argList = new FormalArgListImpl();
	private TypeName _returnType;

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
	public OS_Type getType() {
		return _type;
	}

	// region arglist

	@Override
	public boolean is_simple() {
		return false;
	}

	@Override
	public void postConstruct() {
		// nop
	}

	// endregion

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

	public void setArgs(final ExpressionList ael) {
		// mFal = new FormalArgListImpl();
		// for (IExpression iExpression : ael) {
		// mFal.next().s
		// }
	}

	@Override
	public void setContext(final FuncExprContext ctx) {
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
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

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
