package tripleo.elijah.stages.deduce;

import java.util.*;
import java.util.function.*;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.nextgen.names.i.*;
import tripleo.elijah.lang.nextgen.names.impl.*;
import tripleo.elijah.lang.types.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.deduce.declarations.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.deduce.post_bytecode.*;
import tripleo.elijah.stages.deduce.tastic.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.work.*;

// TODO 10/14 mix of injector and factory ;)
public class DeduceTypes2Injector {
	public __Add_Proc_Table_Listeners new___Add_Proc_Table_Listeners() {
		return new __Add_Proc_Table_Listeners();
	}

	public BaseTableEntry.StatusListener new__StatusListener__BTE_203(final DeduceTypeResolve aDeduceTypeResolve) {
		return aDeduceTypeResolve.new _StatusListener__BTE_203();
	}

	public BaseTableEntry.StatusListener new__StatusListener__BTE_86(final DeduceTypeResolve aDeduceTypeResolve) {
		return aDeduceTypeResolve.new _StatusListener__BTE_86();
	}

	public List<Reactivable> new_ArrayList__Ables() {
		return new ArrayList<>();
	}

	public List<DE3_Active> new_ArrayList__DE3_Active() {
		return new ArrayList<>();
	}

	public List<FunctionInvocation> new_ArrayList__FunctionInvocation() {
		return new ArrayList<>();
	}

	public List<IDeduceResolvable> new_ArrayList__IDeduceResolvable() {
		return new ArrayList<>();
	}

	public List<Runnable> new_ArrayList__Runnable() {
		return new ArrayList<>();
	}

	public List<TypeTableEntry> new_ArrayList__TypeTableEntry(final Collection<TypeTableEntry> aTypeTableEntries) {
		return new ArrayList<>(aTypeTableEntries);
	}

	public CantDecideType new_CantDecideType(final VariableTableEntry aVte,
			final Collection<TypeTableEntry> aTypeTableEntries) {
		return new CantDecideType(aVte, aTypeTableEntries);
	}

	public ClassInvocation.CI_GenericPart new_CI_GenericPart(final List<TypeName> aGenericPart,
			final ClassInvocation aClassInvocation) {
		return aClassInvocation.new CI_GenericPart(aGenericPart);
	}

	public ClassInvocation new_ClassInvocation(final ClassStatement aClassOf, final String aO,
			final @NotNull Supplier<DeduceTypes2> aDeduceTypes2) {
		return new ClassInvocation(aClassOf, aO, aDeduceTypes2);
	}

	public IElementHolder new_ConstructableElementHolder(final OS_Element aE, final IdentIA aIdentIA) {
		return new ConstructableElementHolder(aE, aIdentIA);
	}

	public IVariableConnector new_CtorConnector(final IEvaConstructor aGeneratedFunction,
			final DeduceTypes2 aDeduceTypes2) {
		return new CtorConnector(aGeneratedFunction, aDeduceTypes2);
	}

	public DC_ClassNote new_DC_ClassNote(final ClassStatement aE, final Context aCtx,
			final DeduceCentral aDeduceCentral) {
		return new DC_ClassNote(aE, aCtx, aDeduceCentral);
	}

	public DC_ClassNote.DC_ClassNote_DT2 new_DC_ClassNote_DT2(final IdentTableEntry aIte,
			final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aDeduceTypes2) {
		return new DC_ClassNote.DC_ClassNote_DT2(aIte, aGeneratedFunction, aDeduceTypes2);
	}

	public DE3_Active new_DE3_ActivePTE(final DeduceTypes2 aDeduceTypes2, final ProcTableEntry aPte,
			final ClassInvocation aClassInvocation) {
		return new DE3_ActivePTE(aDeduceTypes2, aPte, aClassInvocation);
	}

	public IElementHolder new_DE3_EH_GroundedVariableStatement(final VariableStatement aE,
			final DeduceElement3_IdentTableEntry aDeduceElement3IdentTableEntry) {
		return aDeduceElement3IdentTableEntry.new DE3_EH_GroundedVariableStatement(
				aE,
				aDeduceElement3IdentTableEntry);
	}

	public DeduceElement3_IdentTableEntry.DE3_ITE_Holder new_DE3_ITE_Holder(final OS_Element aEl,
			final DeduceElement3_IdentTableEntry aDeduceElement3IdentTableEntry) {
		return aDeduceElement3IdentTableEntry.new DE3_ITE_Holder(aEl);
	}

	public DeclAnchor new_DeclAnchor(final OS_Element aDeclAnchor, final DeclAnchor.AnchorType aAnchorType) {
		return new DeclAnchor(aDeclAnchor, aAnchorType);
	}

	public DeduceElement new_DeclTarget(final FunctionDef aBest, final OS_Element aDeclAnchor,
			final DeclAnchor.AnchorType aAnchorType, final DeduceProcCall aDeduceProcCall) throws FCA_Stop {
		return aDeduceProcCall.new DeclTarget(aBest, aDeclAnchor, aAnchorType);
	}

	public DeduceCentral new_DeduceCentral(final DeduceTypes2 aDeduceTypes2) {
		return new DeduceCentral(aDeduceTypes2);
	}

	public DeduceClient1 new_DeduceClient1(final DeduceTypes2 aDeduceTypes2) {
		return new DeduceClient1(aDeduceTypes2);
	}

	public DeduceClient2 new_DeduceClient2(final DeduceTypes2 aDeduceTypes2) {
		return new DeduceClient2(aDeduceTypes2);
	}

	public DeduceClient3 new_DeduceClient3(final DeduceTypes2 aDeduceTypes2) {
		return new DeduceClient3(aDeduceTypes2);
	}

	public DeduceClient4 new_DeduceClient4(final DeduceTypes2 aDeduceTypes2) {
		return new DeduceClient4(aDeduceTypes2, aDeduceTypes2);
	}

	public DeduceElement3_Function new_DeduceElement3_Function(final DeduceTypes2 aDeduceTypes2,
			final BaseEvaFunction aGeneratedFunction) {
		return new DeduceElement3_Function(aDeduceTypes2, aGeneratedFunction);
	}

	public DeduceElement3_IdentTableEntry new_DeduceElement3_IdentTableEntry(final IdentTableEntry aIte) {
		return new DeduceElement3_IdentTableEntry(aIte);
	}

	public DeduceElement3_ProcTableEntry new_DeduceElement3_ProcTableEntry(final ProcTableEntry aPte,
			final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		return new DeduceElement3_ProcTableEntry(aPte, aDeduceTypes2, aGeneratedFunction);
	}

	public DeduceElement3_VariableTableEntry new_DeduceElement3_VariableTableEntry(final VariableTableEntry aVte,
			final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		return new DeduceElement3_VariableTableEntry(aVte, aDeduceTypes2, aGeneratedFunction);
	}

	public DeduceProcCall new_DeduceProcCall(final ProcTableEntry aPte) {
		return new DeduceProcCall(aPte);
	}

	public OS_Element new_DeduceTypes2_OS_SpecialVariable(final VariableTableEntry aEntry,
			final VariableTableType aVariableTableType, final BaseEvaFunction aGeneratedFunction) {
		return new OS_SpecialVariable(aEntry, aVariableTableType, aGeneratedFunction);
	}

	public DeduceCreationContext new_DefaultDeduceCreationContext(final DeduceTypes2 aDeduceTypes2) {
		return new DefaultDeduceCreationContext(aDeduceTypes2);
	}

	public GenerateResultSink new_DefaultGenerateResultSink(final IPipelineAccess aPa) {
		return new DefaultGenerateResultSink(aPa);
	}

	public @NotNull DeferredMember new_DeferredMember(final DeduceElementWrapper aParent,
			final IInvocation aInvocation, final VariableStatementImpl aVariableStatement) {
		return new DeferredMember(aParent, aInvocation, aVariableStatement);
	}

	public DeferredMemberFunction new_DeferredMemberFunction(final OS_Element aParent,
			final IInvocation aInvocation, final FunctionDef aFunctionDef, final DeduceTypes2 aDeduceTypes2,
			final FunctionInvocation aFunctionInvocation) {
		return new DeferredMemberFunction(aParent, aInvocation, aFunctionDef, aDeduceTypes2, aFunctionInvocation);
	}

	public DeferredObject<BaseEvaFunction, Void, Void> new_DeferredObject__BaseEvaFunction() {
		return new DeferredObject<>();
	}

	public DeferredObject<GenType, Diagnostic, Void> new_DeferredObject__GenType() {
		return new DeferredObject<>();
	}

	public Dependencies new_Dependencies(final WorkManager aWorkManager, final DeduceTypes2 aDeduceTypes2) {
		return new Dependencies(aDeduceTypes2, aWorkManager);
	}

	public DG_AliasStatement new_DG_AliasStatement(final AliasStatementImpl aE, final DeduceTypes2 aDt2) {
		return new DG_AliasStatement(aE, aDt2);
	}

	public DG_ClassStatement new_DG_ClassStatement(final ClassStatement aClassStatement) {
		return new DG_ClassStatement(aClassStatement);
	}

	public DG_FunctionDef new_DG_FunctionDef(final FunctionDef aFunctionDef) {
		return new DG_FunctionDef(aFunctionDef);
	}

	public Diagnostic new_Diagnostic_8884(final VariableTableEntry aVte, final BaseEvaFunction aGf) {
		return new DeduceElement3_VariableTableEntry.Diagnostic_8884(aVte, aGf);
	}

	public Diagnostic new_Diagnostic_8885(final VariableTableEntry aVte) {
		return new DeduceElement3_VariableTableEntry.Diagnostic_8885(aVte);
	}

	public FT_FnCallArgs.DoAssignCall new_DoAssignCall(final DeduceClient4 aClient4,
			final BaseEvaFunction aGeneratedFunction, final FT_FnCallArgs aFT_FnCallArgs) {
		return new FT_FnCallArgs.DoAssignCall(aClient4, aGeneratedFunction);
	}

	public DR_Item new_DR_Ident(final IdentTableEntry aIdentTableEntry, final BaseEvaFunction aGeneratedFunction,
			final DeduceTypes2 aDeduceTypes2) {
		return DR_Ident.create(aIdentTableEntry, aGeneratedFunction/* , aDeduceTypes2 */);
	}

	public DR_IdentUnderstandings.ElementUnderstanding new_DR_Ident_ElementUnderstanding(final OS_Element aEl) {
		return new DR_IdentUnderstandings.ElementUnderstanding(aEl);
	}

	public DR_PossibleTypeCI new_DR_PossibleTypeCI(final ClassInvocation aCi, final FunctionInvocation aFi) {
		return new DR_PossibleTypeCI(aCi, aFi);
	}

	public DT_Env new_DT_Env(final ElLog aLOG, final ErrSink aErrSink, final DeduceCentral aCentral) {
		return new DT_Env(aLOG, aErrSink, aCentral);
	}

	public DT_External_2 new_DT_External_2(final IdentTableEntry aIdentTableEntry,
			final OS_Module aModule,
			final ProcTableEntry aPte,
			final FT_FCA_IdentIA.FakeDC4 aDc,
			final ElLog aLOG,
			final Context aCtx,
			final BaseEvaFunction aGeneratedFunction,
			final int aInstructionIndex,
			final IdentIA aIdentIA,
			final VariableTableEntry aVte) {
		return new DT_External_2(aIdentTableEntry, aModule, aPte, aDc, aLOG, aCtx, aGeneratedFunction,
				aInstructionIndex, aIdentIA, aVte);
	}

	public DTR_IdentExpression new_DTR_IdentExpression(final DeduceTypeResolve aDeduceTypeResolve,
			final IdentExpression aIdentExpression, final BaseTableEntry aBte) {
		return new DTR_IdentExpression(aDeduceTypeResolve, aIdentExpression, aBte);
	}

	public DTR_VariableStatement new_DTR_VariableStatement(final DeduceTypeResolve aDeduceTypeResolve,
			final VariableStatement aVariableStatement) {
		return new DTR_VariableStatement(aDeduceTypeResolve, aVariableStatement);
	}

	public ElLog new_ElLog(final String aFileName, final ElLog_.Verbosity aVerbosity, final String aPhase) {
		return new ElLog_(aFileName, aVerbosity, aPhase);
	}

	public EN_Usage new_EN_DeduceUsage(final InstructionArgument aBacklink, final BaseEvaFunction aGf,
			final IdentTableEntry aIte) {
		return new EN_DeduceUsage(aBacklink, aGf, aIte);
	}

	public EN_Usage new_EN_NameUsage(final EN_Name aName, final DeduceElement3_IdentTableEntry aDe3Ite) {
		return new EN_NameUsage(aName, aDe3Ite);
	}

	public EN_Understanding new_ENU_AliasedFrom(final AliasStatement aOrigE) {
		return new ENU_AliasedFrom(aOrigE);
	}

	public EN_Understanding new_ENU_ClassName() {
		return new ENU_ClassName();
	}

	public EN_Understanding new_ENU_ConstructorCallTarget() {
		return new ENU_ConstructorCallTarget();
	}

	public EN_Understanding new_ENU_FunctionCallTarget(final ProcTableEntry aPte) {
		return new ENU_FunctionCallTarget(aPte);
	}

	public EN_Understanding new_ENU_FunctionInvocation(final ProcTableEntry aCallablePte) {
		return new ENU_FunctionInvocation(aCallablePte);
	}

	public EN_Understanding new_ENU_FunctionName() {
		return new ENU_FunctionName();
	}

	public EN_Understanding new_ENU_LangConstVar() {
		return new ENU_LangConstVar();
	}

	public EN_Understanding new_ENU_LookupResult(final LookupResultList aLrl) {
		return new ENU_LookupResult(aLrl);
	}

	public EN_Understanding new_ENU_ResolveToFunction(final FunctionDef aFd) {
		return new ENU_ResolveToFunction(aFd);
	}

	public EN_Understanding new_ENU_TypeTransitiveOver(final ProcTableEntry aPte) {
		return new ENU_TypeTransitiveOver(aPte);
	}

	public EN_Understanding new_ENU_VariableName() {
		return new ENU_VariableName();
	}

	public FT_FCA_IdentIA.FakeDC4 new_FakeDC4(final DeduceClient4 aDc4, final FT_FCA_IdentIA aFT_FCA_IdentIA) {
		return aFT_FCA_IdentIA.new FakeDC4(aDc4);
	}

	public Found_Element_For_ITE new_Found_Element_For_ITE(final BaseEvaFunction aGeneratedFunction,
			final Context aCtx, final DT_Env aEnv, final DeduceClient1 aDeduceClient1) {
		return new Found_Element_For_ITE(aGeneratedFunction, aCtx, aEnv, aDeduceClient1);
	}

	public FT_FCA_ClassStatement new_FT_FCA_ClassStatement(final ClassStatement aEl) {
		return new FT_FCA_ClassStatement(aEl);
	}

	public FT_FCA_IdentIA.FT_FCA_Ctx new_FT_FCA_Ctx(final BaseEvaFunction aGeneratedFunction,
			final TypeTableEntry aTte, final Context aCtx, final ErrSink aErrSink, final DeduceClient4 aDc) {
		return new FT_FCA_IdentIA.FT_FCA_Ctx(aGeneratedFunction, aTte, aCtx, aErrSink, aDc);
	}

	public FT_FCA_FormalArgListItem new_FT_FCA_FormalArgListItem(final FormalArgListItem aFali,
			final BaseEvaFunction aGeneratedFunction) {
		return new FT_FCA_FormalArgListItem(aFali, aGeneratedFunction);
	}

	public FT_FCA_FunctionDef new_FT_FCA_FunctionDef(final FunctionDef aEl, final DeduceTypes2 aDeduceTypes2) {
		return new FT_FCA_FunctionDef(aEl, aDeduceTypes2);
	}

	public FT_FCA_IdentIA new_FT_FCA_IdentIA(final FT_FnCallArgs aFTFnCallArgs, final IdentIA aIdentIA,
			final VariableTableEntry aVte) {
		return new FT_FCA_IdentIA(aFTFnCallArgs, aIdentIA, aVte);
	}

	public FT_FCA_IdentIA.Resolve_VTE new_FT_FCA_IdentIA_Resolve_VTE(final VariableTableEntry aVte,
			final Context aCtx, final ProcTableEntry aPte, final Instruction aInstruction, final FnCallArgs aFca) {
		return new FT_FCA_IdentIA.Resolve_VTE(aVte, aCtx, aPte, aInstruction, aFca);
	}

	public FT_FCA_IdentIA.FT_FCA_ProcedureCall new_FT_FCA_ProcedureCall(final ProcedureCallExpression aPce,
			final Context aCtx, final FT_FCA_IdentIA aFT_FCA_IdentIA) {
		return aFT_FCA_IdentIA.new FT_FCA_ProcedureCall(aPce, aCtx);
	}

	public FT_FCA_VariableStatement new_FT_FCA_VariableStatement(final VariableStatementImpl aVs,
			final BaseEvaFunction aGeneratedFunction) {
		return new FT_FCA_VariableStatement(aVs, aGeneratedFunction);
	}

	public ITastic new_FT_FnCallArgs(final DeduceTypes2 aDeduceTypes2, final FnCallArgs aO) {
		return new FT_FnCallArgs(aDeduceTypes2, aO);
	}

	public FoundElement new_FT_FnCallArgs_DoAssignCall_NullFoundElement(final DeduceClient4 aDc) {
		return new FT_FnCallArgs.NullFoundElement(aDc);
	}

	public FunctionDef new_FunctionDefImpl(final NamespaceStatement aModNs, final Context aContext) {
		return new FunctionDefImpl(aModNs, aContext);
	}

	public IElementHolder new_GenericElementHolder(final OS_Element aBest) {
		return new GenericElementHolder(aBest);
	}

	public IElementHolder new_GenericElementHolderWithDC(final OS_Element aEl, final DeduceClient3 aDc,
			final Resolve_Ident_IA aResolve_Ident_IA) {
		return aResolve_Ident_IA.new GenericElementHolderWithDC(aEl, aDc);
	}

	public IElementHolder new_GenericElementHolderWithType(final OS_Element aElement, final OS_Type aTypeName,
			final DeduceTypes2 aDeduceTypes2) {
		return new GenericElementHolderWithType(aElement, aTypeName, aDeduceTypes2);
	}

	public GenericPart new_GenericPart(final ClassStatement aBest, final NormalTypeName aTyn1) {
		return new GenericPart(aBest, aTyn1);
	}

	public GenType new_GenTypeImpl() {
		return new GenTypeImpl();
	}

	public GenType new_GenTypeImpl(final ClassStatement aKlass) {
		return new GenTypeImpl(aKlass);
	}

	public GenType new_GenTypeImpl(final NamespaceStatement aParent) {
		return new GenTypeImpl(aParent);
	}

	public GenType new_GenTypeImpl(final OS_Type aAttached, final OS_Type aOSType, final boolean aB,
			final TypeName aX, final DeduceTypes2 aDt2, final ErrSink aErrSink, final DeducePhase aPhase) {
		return new GenTypeImpl(aAttached, aOSType, aB, aX, aDt2, aErrSink, aPhase);
	}

	public Map<OS_Element, DG_Item> new_HashMap_DGS() {
		return new HashMap<>();
	}

	public Implement_construct.ICH new_ICH(final GenType aGenType, final Implement_construct aImplement_construct) {
		return aImplement_construct.new ICH(aGenType);
	}

	public @NotNull IdentIA new_IdentIA(int index, @NotNull BaseEvaFunction generatedFunction) {
		return new IdentIA(index, generatedFunction);
	}

	public IdentTableEntry new_IdentTableEntry(final int aI, final IdentExpression aIdentExpression,
			final Context aContext, final BaseEvaFunction aGeneratedFunction) {
		return new IdentTableEntry(aI, aIdentExpression, aContext, aGeneratedFunction);
	}

	public Implement_Calls_ new_Implement_Calls_(final BaseEvaFunction aGeneratedFunction, final Context aFdCtx,
			final InstructionArgument aI2, final ProcTableEntry aFn1, final int aPc,
			final DeduceTypes2 aDeduceTypes2) {
		return new Implement_Calls_(aDeduceTypes2, aGeneratedFunction, aFdCtx, aI2, aFn1, aPc);
	}

	public Implement_construct new_Implement_construct(final DeduceTypes2 aDeduceTypes2,
			final BaseEvaFunction aGeneratedFunction, final Instruction aInstruction) {
		return new Implement_construct(aDeduceTypes2, aGeneratedFunction, aInstruction);
	}

	public IdentTableEntry.ITE_Resolver_Result new_ITE_Resolver_Result(final OS_Element aE) {
		return new IdentTableEntry.ITE_Resolver_Result(aE);
	}

	public List<DT_External> new_LinkedList__DT_External() {
		return new ArrayList<>();
	}

	public List<EvaClass> new_LinkedList__EvaClass() {
		return new ArrayList<>();
	}

	public DeduceLocalVariable.MemberInvocation new_MemberInvocation(final ClassStatement aB,
			final DeduceLocalVariable.MemberInvocation.Role aRole) {
		return new DeduceLocalVariable.MemberInvocation(aB, aRole);
	}

	public NamespaceInvocation new_NamespaceInvocation(final NamespaceStatement aModNs) {
		return new NamespaceInvocation(aModNs);
	}

	public NamespaceStatement new_NamespaceStatementImpl(final OS_Module aModule, final Context aContext) {
		return new NamespaceStatementImpl(aModule, aContext);
	}

	public IVariableConnector new_NullConnector() {
		return new NullConnector();
	}

	public OS_Type new_OS_BuiltinType(final BuiltInTypes aBuiltInType) {
		return new OS_BuiltinType(aBuiltInType);
	}

	public OS_Type new_OS_UnknownType(final OS_Element aElement) {
		return new OS_UnknownType(aElement);
	}

	public OS_Type new_OS_UnknownType(final StatementWrapperImpl aStatementWrapper) {
		return new OS_UnknownType(aStatementWrapper);
	}

	public OS_UserType new_OS_UserType(final TypeName aTypeName) {
		return new OS_UserType(aTypeName);
	}

	public BaseTableEntry.StatusListener new_ProcTableListener(final ProcTableEntry aPte,
			final BaseEvaFunction aGeneratedFunction, final DeduceClient2 aO) {
		return new ProcTableListener(aPte, aGeneratedFunction, aO);
	}

	public <B> PromiseExpectation<B> new_PromiseExpectation(final ExpectationBase aBase, final String aDesc,
			final DeduceTypes2 aDeduceTypes2) {
		return new PromiseExpectation<B>(aDeduceTypes2, aBase, aDesc);
	}

	public PromiseExpectations new_PromiseExpectations(final DeduceTypes2 aDeduceTypes2) {
		return new PromiseExpectations();
	}

	public RegularTypeName new_RegularTypeNameImpl(final Context aContext) {
		return new RegularTypeNameImpl(aContext);
	}

	public Resolve_each_typename new_Resolve_each_typename(final DeducePhase aPhase,
			final DeduceTypes2 aDeduceTypes2, final ErrSink aErrSink) {
		return new Resolve_each_typename(aDeduceTypes2, aPhase, aDeduceTypes2, aErrSink);
	}

	public Resolve_Ident_IA new_Resolve_Ident_IA(final DeduceClient3 aDeduceClient3, final Context aContext,
			final Resolve_Ident_IA.DeduceElementIdent aDei, final BaseEvaFunction aGeneratedFunction,
			final FoundElement aFoundElement, final ErrSink aErrSink) {
		return new Resolve_Ident_IA(aDeduceClient3, aContext, aDei, aGeneratedFunction, aFoundElement, aErrSink);
	}

	public Resolve_Ident_IA new_Resolve_Ident_IA(final DeduceClient3 aDeduceClient3, final Context aContext,
			final IdentIA aIdentIA, final BaseEvaFunction aGeneratedFunction, final FoundElement aFoundElement,
			final ErrSink aErrSink) {
		return new Resolve_Ident_IA(aDeduceClient3, aContext, aIdentIA, aGeneratedFunction, aFoundElement,
				aErrSink);
	}

	public Resolve_Ident_IA2 new_Resolve_Ident_IA2(final DeduceTypes2 aDeduceTypes2, final ErrSink aErrSink,
			final DeducePhase aPhase, final BaseEvaFunction aGeneratedFunction, final FoundElement aFoundElement) {
		return new Resolve_Ident_IA2(aDeduceTypes2, aErrSink, aPhase, aGeneratedFunction, aFoundElement);
	}

	public Resolve_Variable_Table_Entry new_Resolve_Variable_Table_Entry(final BaseEvaFunction aGeneratedFunction,
			final Context aContext, final DeduceTypes2 aDeduceTypes2) {
		return new Resolve_Variable_Table_Entry(aGeneratedFunction, aContext, aDeduceTypes2);
	}

	public Diagnostic new_ResolveError(final IdentExpression aIdent, final LookupResultList aLrl) {
		return new ResolveError(aIdent, aLrl);
	}

	public Diagnostic new_ResolveError(final TypeName aX, final LookupResultList aLrl) {
		return new ResolveError(aX, aLrl);
	}

	public Resolve_Ident_IA2.RIA_Clear_98 new_RIA_Clear_98(final IdentTableEntry aIdte,
			final VariableStatementImpl aVs, final Context aCtx, final Resolve_Ident_IA2 aResolve_Ident_IA2) {
		return aResolve_Ident_IA2.new RIA_Clear_98(aIdte, aVs, aCtx);
	}

	public setup_GenType_Action new_SGTA_RegisterClassInvocation(final ClassStatement aClassStatement,
			final DeducePhase aPhase) {
		return new SGTA_RegisterClassInvocation(aClassStatement, aPhase);
	}

	public setup_GenType_Action new_SGTA_RegisterNamespaceInvocation(final NamespaceStatement aNamespaceStatement,
			final DeducePhase aPhase) {
		return new SGTA_RegisterNamespaceInvocation(aNamespaceStatement, aPhase);
	}

	public setup_GenType_Action new_SGTA_SetClassInvocation() {
		return new SGTA_SetClassInvocation();
	}

	public setup_GenType_Action new_SGTA_SetNamespaceInvocation() {
		return new SGTA_SetNamespaceInvocation();
	}

	public SGTA_SetResolvedClass new_SGTA_SetResolvedClass(final ClassStatement aKlass) {
		return new SGTA_SetResolvedClass(aKlass);
	}

	public setup_GenType_Action new_SGTA_SetResolvedNamespace(final NamespaceStatement aNamespaceStatement) {
		return new SGTA_SetResolvedNamespace(aNamespaceStatement);
	}

	public StatementWrapperImpl new_StatementWrapperImpl(final IExpression aLeft, final Context aO,
			final OS_Element aO1) {
		return new StatementWrapperImpl(aLeft, aO, aO1);
	}

	public BaseTableEntry.StatusListener new_StatusListener__RIA__176(final IdentTableEntry aY,
			final FoundElement aFoundElement, final Resolve_Ident_IA aResolve_Ident_IA) {
		return aResolve_Ident_IA.new StatusListener__RIA__176(aY, aFoundElement);
	}

	public ITasticMap new_TasticMap() {
		return new TasticMap_1();
	}

	public TypeTableEntry new_TypeTableEntry(final int aI, final TypeTableEntry.Type aType, final OS_Type aTypeName,
			final IdentExpression aIdent, final TableEntryIV aO) {
		return new TypeTableEntry(aI, aType, aTypeName, aIdent, aO);
	}

	public ITE_Resolver new_Unnamed_ITE_Resolver1(final DeduceTypes2 aDeduceTypes2, final IdentTableEntry aIte,
			final BaseEvaFunction aGeneratedFunction, final Context aContext) {
		return new Unnamed_ITE_Resolver1(aDeduceTypes2, aIte, aGeneratedFunction, aContext);
	}

	public WorkJob new_WlDeduceFunction(final WorkJob aGen, final List<BaseEvaFunction> aColl,
			final DeduceTypes2 aDeduceTypes2) {
		return new WlDeduceFunction(aDeduceTypes2, aGen, aColl);
	}

	public WlGenerateClass new_WlGenerateClass(final GenerateFunctions aGenerateFunctions,
			final ClassInvocation aCi, final DeducePhase.GeneratedClasses aGeneratedClasses,
			final ICodeRegistrar aCodeRegistrar) {
		return new WlGenerateClass(aGenerateFunctions, aCi, aGeneratedClasses, aCodeRegistrar);
	}

	public WlGenerateCtor new_WlGenerateCtor(final GenerateFunctions aGenerateFunctions,
			final FunctionInvocation aFi,
			final IdentExpression aIdentExpression,
			final ICodeRegistrar aCodeRegistrar) {
		return new WlGenerateCtor(aGenerateFunctions, aFi, aIdentExpression, aCodeRegistrar);
	}

	public WlGenerateCtor new_WlGenerateCtor(final OS_Module aModule,
			final IdentExpression aNameNode,
			final FunctionInvocation aFunctionInvocation,
			final DeduceCreationContext aCl) {
		return new WlGenerateCtor(aModule, aFunctionInvocation.getFunction().getNameNode(), aFunctionInvocation,
				aCl);
	}

	public @NotNull WlGenerateDefaultCtor new_WlGenerateDefaultCtor(final GenerateFunctions aGf,
			final FunctionInvocation aDependentFunction,
			final DeduceCreationContext aDeduceCreationContext,
			final ICodeRegistrar aCodeRegistrar) {
		return new WlGenerateDefaultCtor(aGf, aDependentFunction, aDeduceCreationContext, aCodeRegistrar);
	}

	public WlGenerateDefaultCtor new_WlGenerateDefaultCtor(final OS_Module aModule,
			final FunctionInvocation aFunctionInvocation,
			final @NotNull DeduceCreationContext aCl) {
		return new WlGenerateDefaultCtor(aModule, aFunctionInvocation, aCl);
	}

	public @Nullable WlGenerateFunction new_WlGenerateFunction(final GenerateFunctions aGf,
			final FunctionInvocation aDependentFunction,
			final ICodeRegistrar aCodeRegistrar) {
		return new WlGenerateFunction(aGf, aDependentFunction, aCodeRegistrar);
	}

	public WlGenerateFunction new_WlGenerateFunction(final OS_Module aModule,
			final FunctionInvocation aDependentFunction,
			final DeduceCreationContext aCl) {
		return new WlGenerateFunction(aModule, aDependentFunction, aCl);
	}

	public WlGenerateNamespace new_WlGenerateNamespace(final GenerateFunctions aGenerateFunctions,
			final NamespaceInvocation aCi,
			final DeducePhase.GeneratedClasses aGeneratedClasses,
			final ICodeRegistrar aCodeRegistrar) {
		return new WlGenerateNamespace(aGenerateFunctions, aCi, aGeneratedClasses, aCodeRegistrar);
	}

	public WorkList new_WorkList() {
		return new WorkList__();
	}

	public WorkManager new_WorkManager() {
		return new WorkManager__();
	}

	public Zero new_Zero(final DeduceTypes2 aDeduceTypes2) {
		return new Zero(aDeduceTypes2);
	}

	@SuppressWarnings("UnnecessaryLocalVariable")
	public DerivedClassInvocation2 new_DerivedClassInvocation2(final ClassStatement aDeclAnchor,
			final ClassInvocation aDeclaredInvocation,
			final ReadySupplier_1<DeduceTypes2> dtSupplier) {
		final DerivedClassInvocation2 result = new DerivedClassInvocation2() {
			@Override
			public void into(final Eventual<IInvocation> aInvocationP) {
				if (this.carrier == null) {
					this.carrier = new_DerivedClassInvocation(aDeclAnchor, aDeclaredInvocation, dtSupplier);
				}

				aInvocationP.resolve(this.carrier);
			}
		};
		return result;
	}

	public IInvocation new_DerivedClassInvocation(final ClassStatement aDeclAnchor,
			final ClassInvocation aDeclaredInvocation, final Supplier<DeduceTypes2> aDeduceTypes2) {
		return new DerivedClassInvocation(aDeclAnchor, aDeclaredInvocation, aDeduceTypes2);
	}

	public abstract static class DerivedClassInvocation2 {
		protected IInvocation carrier;

		public IInvocation asInvocation() {
			return carrier;
		}

		public abstract void into(final Eventual<IInvocation> aInvocationP);
	}
}
