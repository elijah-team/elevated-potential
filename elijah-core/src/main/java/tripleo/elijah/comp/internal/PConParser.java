package tripleo.elijah.comp.internal;

import antlr.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.builder.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.imports.*;
import tripleo.elijah.lang.types.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public class PConParser {

	public AccessNotation new_AccessNotationImpl() {
		return new AccessNotationImpl();
	}

	public InvariantStatementPart new_InvariantStatementPartImpl(final IInvariantStatement aCr, final Token aI1) {
		return new InvariantStatementPartImpl(aCr, aI1);
	}

	public NamespaceContext__ new_NamespaceContext(final Context aCur, final NamespaceStatement aCls) {
		return new NamespaceContext__(aCur, aCls);
	}

	public FunctionDef new_FunctionDefImpl(final OS_Element aParent, final Context aCtx) {
		return new FunctionDefImpl(aParent, aCtx);
	}

	public NormalTypeName new_RegularTypeNameImpl(final Context aCur) {
		return new RegularTypeNameImpl(aCur);
	}

	public Postcondition new_PostconditionImpl() {
		return new PostconditionImpl();
	}

	public IExpression new_GetItemExpressionImpl(final IExpression aEe, final IExpression aExpr) {
		return new GetItemExpressionImpl(aEe, aExpr);
	}

	public IExpression new_SetItemExpressionImpl(final GetItemExpression aEe, final IExpression aExpr) {
		return new SetItemExpressionImpl(aEe, aExpr);
	}

	public ProcedureCallExpression new_ProcedureCallExpressionImpl() {
		return new ProcedureCallExpressionImpl();
	}

	public TypeCastExpression new_TypeCastExpressionImpl() {
		return new TypeCastExpressionImpl();
	}

	public Precondition new_PreconditionImpl() {
		return new PreconditionImpl();
	}

	public IExpression new_SubExpressionImpl(final IExpression aEe) {
		return new SubExpressionImpl(aEe);
	}

	public FuncExpr new_FuncExprImpl() {
		return new FuncExprImpl();
	}

	public IExpression new_ListExpressionImpl() {
		return new ListExpressionImpl();
	}

	public ExpressionList new_ExpressionListImpl() {
		return new ExpressionListImpl();
	}

	public ModuleContext new_ModuleContext(final OS_Module aModule) {
		return new ModuleContext__(aModule);
	}

	public Context new_PackageContext(final Context aCur, final OS_Package aPkg) {
		return new PackageContext(aCur, aPkg);
	}

	public NamespaceStatement new_NamespaceStatementImpl(final OS_Element aCont, final Context aCur) {
		return new NamespaceStatementImpl(aCont, aCur);
	}

	public Qualident new_QualidentImpl() {
		return new QualidentImpl();
	}

	public QualidentList new_QualidentListImpl() {
		return new QualidentListImpl();
	}

	public OS_Type new_OS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
		return new OS_BuiltinType(aBuiltInTypes);
	}

	public IExpression new_TypeCheckExpressionImpl(final IExpression aEe, final TypeName aTn) {
		return new TypeCheckExpressionImpl(aEe, aTn);
	}

	public Scope3 new_Scope3Impl(final OS_Element aParent) {
		return new Scope3Impl(aParent);
	}

	public SyntacticBlock new_SyntacticBlockImpl(final OS_Element aParent) {
		return new SyntacticBlockImpl(aParent);
	}

	public SyntacticBlockContext new_SyntacticBlockContext(final SyntacticBlock aSb, final Context aCur) {
		return new SyntacticBlockContext(aSb, aCur);
	}

	public TypeAliasBuilder new_TypeAliasBuilder() {
		return new TypeAliasBuilder();
	}

	public TypeNameList new_TypeNameListImpl() {
		return new TypeNameListImpl();
	}

	public TypeOfTypeName new_TypeOfTypeNameImpl(final Context aCur) {
		return new TypeOfTypeNameImpl(aCur);
	}

	public IExpression new_UnaryExpressionImpl(final ExpressionKind aExpressionKind, final IExpression aEe) {
		return new UnaryExpressionImpl(aExpressionKind, aEe);
	}

	public LoopContext__ new_LoopContext(final Context aCur, final Loop aLoop) {
		return new LoopContext__(aCur, aLoop);
	}

	public WithStatement new_WithStatementImpl(final OS_Element aParent) {
		return new WithStatementImpl(aParent);
	}

	public IWithContext new_WithContext(final WithStatement aWs, final Context aCur) {
		return new WithContext(aWs, aCur);
	}

	public AliasStatement new_AliasStatementImpl(final OS_Element aCont) {
		return new AliasStatementImpl(aCont);
	}

	public AnnotationClause new_AnnotationClauseImpl() {
		return new AnnotationClauseImpl();
	}

	public AnnotationPart new_AnnotationPartImpl() {
		return new AnnotationPartImpl();
	}

	public ClassHeader new_ClassHeaderImpl(final boolean aExtends, final List<AnnotationClause> aAs) {
		return new ClassHeaderImpl(aExtends, aAs);
	}

	public ClassStatement new_ClassStatementImpl(final OS_Element aParent, final Context aCctx) {
		return new ClassStatementImpl(aParent, aCctx);
	}

	public IExpression new_StringExpressionImpl(final Token aS) {
		return new StringExpressionImpl(aS);
	}

	public IExpression new_CharLitExpressionImpl(final Token aC) {
		return new CharLitExpressionImpl(aC);
	}

	public IExpression new_NumericExpressionImpl(final Token aN) {
		return new NumericExpressionImpl(aN);
	}

	public IExpression new_FloatExpressionImpl(final Token aF) {
		return new FloatExpressionImpl(aF);
	}

	public DefFunctionDef new_DefFunctionDefImpl(final OS_Element aParent, final Context aCtx) {
		return new DefFunctionDefImpl(aParent, aCtx);
	}

	public IExpression new_DotExpressionImpl(final IExpression aDotExpressionLeft, final IdentExpression aDotExpressionRight) {
		return new DotExpressionImpl(aDotExpressionLeft, aDotExpressionRight);
	}

	public FormalArgList new_FormalArgListImpl() {
		return new FormalArgListImpl();
	}

	public FuncExprContext new_FuncExprContext(final Context aCur, final FuncExpr aPc) {
		return new FuncExprContext(aCur, aPc);
	}

	public FunctionBody new_FunctionBodyEmptyImpl() {
		return new FunctionBodyEmptyImpl();
	}

	public FunctionBody new_FunctionBodyImpl() {
		return new FunctionBodyImpl();
	}

	public FunctionHeader new_FunctionHeaderImpl() {
		return new FunctionHeaderImpl();
	}

	public FuncTypeName new_FuncTypeNameImpl(final Context aCur) {
		return new FuncTypeNameImpl(aCur);
	}

	public GenericTypeName new_GenericTypeNameImpl(final Context aCur) {
		return new GenericTypeNameImpl(aCur);
	}

	public IdentExpression new_IdentExpressionImpl(final Token aR1, final String aFilename, final Context aCur) {
		return new IdentExpressionImpl(aR1, aFilename, aCur);
	}

	public IdentList new_IdentListImpl() {
		return new IdentListImpl();
	}

	public ImportStatement new_RootedImportStatement(final OS_Element aEl) {
		return new RootedImportStatement(aEl);
	}

	public ImportContext new_ImportContext(final Context aCur, final ImportStatement aPc) {
		return new ImportContext(aCur, aPc);
	}

	public ImportStatement new_AssigningImportStatement(final OS_Element aEl) {
		return new AssigningImportStatement(aEl);
	}

	public ImportStatement new_QualifiedImportStatement(final OS_Element aEl) {
		return new QualifiedImportStatement(aEl);
	}

	public ImportStatement new_NormalImportStatement(final OS_Element aEl) {
		return new NormalImportStatement(aEl);
	}

	public IndexingStatement new_IndexingStatementImpl(final OS_Module aModule) {
		return new IndexingStatementImpl(aModule);
	}

	public IndexingItem new_IndexingItemImpl(final Token aI1, final ExpressionList aEl) {
		return new IndexingItemImpl(aI1, aEl);
	}

	public ProcedureCallExpression new_ProcedureCallExpressionImpl(final IExpression ee, final ExpressionList el) {
		final ProcedureCallExpression pce = new_ProcedureCallExpressionImpl();
		pce.identifier(ee);
		pce.setArgs(el);
		return pce;
	}
}
