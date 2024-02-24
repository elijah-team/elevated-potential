package tripleo.elijah.comp;

import antlr.Token;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.*;

public class PCon {
	public CiExpression ExpressionBuilder_build(final CiExpression aEe,
												final CiExpressionKind aEk,
												final CiExpression aE2) {
		return CiExpressionBuilder.build(aEe, aEk, aE2);
	}

	public CiExpression newCharLitExpressionImpl(final Token aC) {
		return new CiCharLitExpressionImpl(aC);
	}

	public CompilerInstructions newCompilerInstructionsImpl() {
		return new CompilerInstructionsImpl();
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

	public CiIdentExpression newIdentExpressionImpl(final Token aToken, final String aFilename, final Object aCur) {
		return new CiIdentExpressionImpl(aToken);//, aFilename, aCur);
	}

	public CiIdentExpression newIdentExpressionImpl(final Token aToken, final Object aCur) {
		return new CiIdentExpressionImpl(aToken, aCur);
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

	//public OS_Type newOS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
	//	return new OS_BuiltinType(aBuiltInTypes);
	//}

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
}
