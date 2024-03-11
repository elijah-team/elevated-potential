package tripleo.elijah_durable_elevated.elijah.comp;

import antlr.Token;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiProcedureCallExpression;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci.cii.GetItemExpression;
import tripleo.elijah.ci.cii.IdentExpression;
import tripleo.elijah.ci.cii.Qualident;
import tripleo.elijah.ci.cil.CharLitExpressionImpl;
import tripleo.elijah.ci.cil.CiListExpressionImpl;
import tripleo.elijah.ci.cil.DotExpressionImpl;
import tripleo.elijah.ci.cil.ExpressionBuilder;
import tripleo.elijah.ci.cil.FloatExpressionImpl;
import tripleo.elijah.ci.cil.GetItemExpressionImpl;
import tripleo.elijah.ci.cil.IdentExpressionImpl;
import tripleo.elijah.ci.cil.NumericExpressionImpl;
import tripleo.elijah.ci.cil.QualidentImpl;
import tripleo.elijah.ci.cil.SetItemExpressionImpl;
import tripleo.elijah.ci.cil.StringExpressionImpl;
import tripleo.elijah.ci.cil.SubExpressionImpl;
import tripleo.elijah_durable_elevated.elijah.ci_impl.CiExpressionListImpl;
import tripleo.elijah_durable_elevated.elijah.ci_impl.CiProcedureCallExpressionImpl;
import tripleo.elijah_durable_elevated.elijah.ci_impl.CompilerInstructionsImpl;
import tripleo.elijah_durable_elevated.elijah.ci_impl.GenerateStatementImpl;
import tripleo.elijah_durable_elevated.elijah.ci_impl.LibraryStatementPartImpl;
import tripleo.elijah.lang.i.OS_Type;

public class PCon {
	public CiExpression ExpressionBuilder_build(final CiExpression aEe, final ExpressionKind aEk,
											   final CiExpression aE2) {
		return ExpressionBuilder.build(aEe, aEk, aE2);
	}

	public CiExpression newCharLitExpressionImpl(final Token aC) {
		return new CharLitExpressionImpl(aC);
	}

	public CompilerInstructions newCompilerInstructionsImpl() {
		return new CompilerInstructionsImpl();
	}

	public CiExpression newDotExpressionImpl(final CiExpression aDotExpressionLeft, final IdentExpression aDotExpressionRightIdent) {
		return new DotExpressionImpl(aDotExpressionLeft, aDotExpressionRightIdent);
	}

	public CiExpressionList newExpressionListImpl() {
		return new CiExpressionListImpl();
	}

	public CiExpression newFloatExpressionImpl(final Token aF) {
		return new FloatExpressionImpl(aF);
	}

	public GenerateStatement newGenerateStatementImpl() {
		return new GenerateStatementImpl();
	}

	public CiExpression newGetItemExpressionImpl(final CiExpression aEe, final CiExpression aExpr) {
		return new GetItemExpressionImpl(aEe, aExpr);
	}

	public IdentExpression newIdentExpressionImpl(final Token aToken, final String aFilename, final Object aCur) {
		return new IdentExpressionImpl(aToken);//, aFilename, aCur);
	}

	public IdentExpression newIdentExpressionImpl(final Token aToken, final Object aCur) {
		return new IdentExpressionImpl(aToken, aCur);
	}

	public LibraryStatementPart newLibraryStatementPartImpl() {
		return new LibraryStatementPartImpl();
	}

	public CiExpression newListExpressionImpl() {
		return new CiListExpressionImpl();
	}

	public CiExpression newNumericExpressionImpl(final Token aN) {
		return new NumericExpressionImpl(aN);
	}

	//public OS_Type newOS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
	//	return new OS_BuiltinType(aBuiltInTypes);
	//}

	public CiProcedureCallExpression newProcedureCallExpressionImpl() {
		return new CiProcedureCallExpressionImpl();
	}

	public Qualident newQualidentImpl() {
		return new QualidentImpl();
	}

	public CiExpression newSetItemExpressionImpl(final GetItemExpression aEe, final CiExpression aExpr) {
		return new SetItemExpressionImpl(aEe, aExpr);
	}

	public CiExpression newStringExpressionImpl(final Token aS) {
		return new StringExpressionImpl(aS);
	}

	public CiExpression newSubExpressionImpl(final CiExpression aEe) {
		return new SubExpressionImpl(aEe);
	}

	public CiExpressionList newCiExpressionListImpl() {
		return new CiExpressionListImpl();
	}

	public CiProcedureCallExpression newCiProcedureCallExpressionImpl() {
		return new CiProcedureCallExpressionImpl();
	}

	public CiExpression ExpressionBuilder_build(final CiExpression aEe, final ExpressionKind aE2, final CiExpression aE3, final OS_Type aT) {
		// TODO 10/15 look at me
		return ExpressionBuilder_build(aEe, aE2, aE3);
	}
}
