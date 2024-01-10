package tripleo.elijah.comp.rubicon;

import antlr.Token;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang2.BuiltInTypes;

public class PCon {
	public IExpression ExpressionBuilder_build(final IExpression aEe, final ExpressionKind aEk,
											   final IExpression aE2) {
		return ExpressionBuilder.build(aEe, aEk, aE2);
	}

	public IExpression newCharLitExpressionImpl(final Token aC) {
		return new CharLitExpressionImpl(aC);
	}

	public CompilerInstructions newCompilerInstructionsImpl() {
		return new CompilerInstructionsImpl();
	}

	public IExpression newDotExpressionImpl(final IExpression aDotExpressionLeft, final IdentExpression aDotExpressionRightIdent) {
		return new DotExpressionImpl(aDotExpressionLeft, aDotExpressionRightIdent);
	}

	public ExpressionList newExpressionListImpl() {
		return new ExpressionListImpl();
	}

	public IExpression newFloatExpressionImpl(final Token aF) {
		return new FloatExpressionImpl(aF);
	}

	public GenerateStatement newGenerateStatementImpl() {
		return new GenerateStatementImpl();
	}

	public IExpression newGetItemExpressionImpl(final IExpression aEe, final IExpression aExpr) {
		return new GetItemExpressionImpl(aEe, aExpr);
	}

	public IdentExpression newIdentExpressionImpl(final Token aR1, final String aFilename, final Context aCur) {
		return new IdentExpressionImpl(aR1, aFilename, aCur);
	}

	public IdentExpression newIdentExpressionImpl(final Token aR1, final Context aCur) {
		return new IdentExpressionImpl(aR1, aCur);
	}

	public LibraryStatementPart newLibraryStatementPartImpl() {
		return new LibraryStatementPartImpl();
	}

	public IExpression newListExpressionImpl() {
		return new ListExpressionImpl();
	}

	public IExpression newNumericExpressionImpl(final Token aN) {
		return new NumericExpressionImpl(aN);
	}

	public OS_Type newOS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
		return new OS_BuiltinType(aBuiltInTypes);
	}

	public ProcedureCallExpression newProcedureCallExpressionImpl() {
		return new ProcedureCallExpressionImpl();
	}

	public Qualident newQualidentImpl() {
		return new QualidentImpl();
	}

	public IExpression newSetItemExpressionImpl(final GetItemExpression aEe, final IExpression aExpr) {
		return new SetItemExpressionImpl(aEe, aExpr);
	}

	public IExpression newStringExpressionImpl(final Token aS) {
		return new StringExpressionImpl(aS);
	}

	public IExpression newSubExpressionImpl(final IExpression aEe) {
		return new SubExpressionImpl(aEe);
	}

	public CiExpressionList newCiExpressionListImpl() {
		return new CiExpressionListImpl();
	}

	public CiProcedureCallExpression newCiProcedureCallExpressionImpl() {
		return new CiProcedureCallExpressionImpl();
	}

	public IExpression ExpressionBuilder_build(final IExpression aEe, final ExpressionKind aE2, final IExpression aE3, final OS_Type aT) {
		// TODO 10/15 look at me
		return ExpressionBuilder_build(aEe, aE2, aE3);
	}
}
