package tripleo.elijah.comp.rubicon;

import antlr.Token;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiGetItemExpression;
import tripleo.elijah.ci.CiIdentExpression;
import tripleo.elijah.ci.CiProcedureCallExpression;
import tripleo.elijah.ci.CiQualident;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci_impl.*;
import tripleo.elijah.lang.impl.ExpressionBuilder;
import tripleo.elijjah.EzParser;

public class PCon {
	public CiExpression ExpressionBuilder_build(final CiExpression aEe, final CiExpressionKind aEk,
											   final CiExpression aE2) {
		return CiExpressionBuilder.build(aEe, aEk, aE2);
	}

	public CiExpression newCharLitExpressionImpl(final Token aC) {
		return new CiCharLitExpressionImpl(aC);
	}

	public CompilerInstructions newCompilerInstructionsImpl() {
		return null;//new CompilerInstructions(){};
	}

	public CiExpression newDotExpressionImpl(final CiExpression aDotExpressionLeft, final CiIdentExpression aDotExpressionRightIdent) {
		return new CiDotExpressionImpl(aDotExpressionLeft, aDotExpressionRightIdent);
	}

	public CiExpressionList newExpressionListImpl() {
		return new CiExpressionListImpl();
	}

	public CiExpression newFloatExpressionImpl(final Token aF) {
		return new CiFloatExpressionImpl(aF);
	}

	public GenerateStatement newGenerateStatementImpl() {
		return new GenerateStatementImpl();
	}

	public CiExpression newGetItemExpressionImpl(final CiExpression aEe, final CiExpression aExpr) {
		return new CiGetItemExpressionImpl(aEe, aExpr);
	}

//	public CiIdentExpression newIdentExpressionImpl(final Token aR1, final String aFilename, final Object aCur) {
//		return new CiIdentExpressionImpl(aR1);//, aFilename, aCur);
//	}

	public CiIdentExpression newIdentExpressionImpl(final Token aR1, final Object aCur) {
		return new CiIdentExpressionImpl(aR1, aCur);
	}

	public LibraryStatementPart newLibraryStatementPartImpl() {
		return new LibraryStatementPartImpl();
	}

	public CiExpression newListExpressionImpl() {
		return new CiListExpressionImpl();
	}

	public CiExpression newNumericExpressionImpl(final Token aN) {
		return new CiNumericExpressionImpl(aN);
	}

//	public OS_Type newOS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
//		return new OS_BuiltinType(aBuiltInTypes);
//	}

	public CiProcedureCallExpression newProcedureCallExpressionImpl() {
		return new CiProcedureCallExpressionImpl();
	}

	public CiQualident newQualidentImpl() {
		return new CiQualidentImpl();
	}

	public CiExpression newSetItemExpressionImpl(final CiGetItemExpression aEe, final CiExpression aExpr) {
		return new CiSetItemExpressionImpl(aEe, aExpr);
	}

	public CiExpression newStringExpressionImpl(final Token aS) {
		return new CiStringExpressionImpl(aS);
	}

	public CiExpression newSubExpressionImpl(final CiExpression aEe) {
		return new CiSubExpressionImpl(aEe);
	}

	public CiExpressionList newCiExpressionListImpl() {
		return new CiExpressionListImpl();
	}

	public CiProcedureCallExpression newCiProcedureCallExpressionImpl() {
		return new CiProcedureCallExpressionImpl();
	}

	public CiExpression ExpressionBuilder_build(final CiExpression aEe, final CiExpressionKind aE2, final CiExpression aE3, final Object aT) {
		// TODO 10/15 look at me
		return ExpressionBuilder_build(aEe, aE2, aE3);
	}

	public static void connectDefault(EzParser parser) {
		final PCon pCon = new PCon();
		final CompilerInstructions ci = pCon.newCompilerInstructionsImpl();

		parser.pcon = pCon;
		parser.ci   = ci;
	}

	public CiProcedureCallExpression newCiProcedureCallExpressionImpl(CiExpression ee, CiExpressionList el) {
		var result = newCiProcedureCallExpressionImpl();
		result.setExpressionList(el);
		result.setLeft(ee);
		return null;
	}
}
