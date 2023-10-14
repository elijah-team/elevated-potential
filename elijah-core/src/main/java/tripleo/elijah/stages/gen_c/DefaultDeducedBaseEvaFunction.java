package tripleo.elijah.stages.gen_c;

import io.reactivex.rxjava3.subjects.*;
import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.util.range.Range;

import java.util.*;

public class DefaultDeducedBaseEvaFunction implements DeducedBaseEvaFunction {
	private final BaseEvaFunction carrier;

	public DefaultDeducedBaseEvaFunction(final BaseEvaFunction aEvaFunction) {
		carrier = aEvaFunction;
	}

	public void addDependentFunction(@NotNull final FunctionInvocation aFunction) {
		carrier.addDependentFunction(aFunction);
	}

	public void addDependentType(@NotNull final GenType aType) {
		carrier.addDependentType(aType);
	}

	public @NotNull List<FunctionInvocation> dependentFunctions() {
		return carrier.dependentFunctions();
	}

	public Subject<FunctionInvocation> dependentFunctionSubject() {
		return carrier.dependentFunctionSubject();
	}

	public @NotNull List<GenType> dependentTypes() {
		return carrier.dependentTypes();
	}

	public Subject<GenType> dependentTypesSubject() {
		return carrier.dependentTypesSubject();
	}

	public void noteDependencies(final @NotNull Dependency d) {
		carrier.noteDependencies(d);
	}

	@NotNull
	public DT_Resolvabley _getIdentIAResolvable(final @NotNull IdentIA aIdentIA) {
		return carrier._getIdentIAResolvable(aIdentIA);
	}

	@Override
	public int add(final InstructionName aName, final List<InstructionArgument> args_, final Context ctx) {
		return carrier.add(aName, args_, ctx);
	}

	@Override
	public void addContext(final Context context, final Range r) {
		carrier.addContext(context, r);
	}

	@Override
	public void addElement(final OS_Element aElement, final DeduceElement aDeduceElement) {
		carrier.addElement(aElement, aDeduceElement);
	}

	@Override
	public int addIdentTableEntry(final IdentExpression ident, final Context context) {
		return carrier.addIdentTableEntry(ident, context);
	}

	@Override
	public @NotNull Label addLabel() {
		return carrier.addLabel();
	}

	@Override
	public @NotNull Label addLabel(final String base_name, final boolean append_int) {
		return carrier.addLabel(base_name, append_int);
	}

	@Override
	public int addVariableTableEntry(final String name, final VariableTableType vtt, final TypeTableEntry type, final OS_Element el) {
		return carrier.addVariableTableEntry(name, vtt, type, el);
	}

	public @NotNull DR_Type buildDrTypeFromNonGenericTypeName(final TypeName aNonGenericTypeName) {
		return carrier.buildDrTypeFromNonGenericTypeName(aNonGenericTypeName);
	}

	public Map<OS_Element, DeduceElement> elements() {
		return carrier.elements();
	}

	@Override
	public String expectationString() {
		return carrier.expectationString();
	}

	@Override
	public @Nullable Label findLabel(final int index) {
		return carrier.findLabel(index);
	}

	@Override
	public @NotNull InstructionArgument get_assignment_path(final @NotNull IExpression expression, final @NotNull GenerateFunctions generateFunctions, final @NotNull Context context) {
		return carrier.get_assignment_path(expression, generateFunctions, context);
	}

	@Override
	public int getCode() {
		return carrier.getCode();
	}

	@Override
	@NotNull
	public ConstantTableEntry getConstTableEntry(final int index) {
		return carrier.getConstTableEntry(index);
	}

	@Override
	public Context getContextFromPC(final int pc) {
		return carrier.getContextFromPC(pc);
	}

	@Override
	@NotNull
	public Dependency getDependency() {
		return carrier.getDependency();
	}

	@Override
	@NotNull
	public String getFunctionName() {
		return carrier.getFunctionName();
	}

	@Override
	public EvaNode getGenClass() {
		return carrier.getGenClass();
	}

	public @NotNull DR_Ident getIdent(final IdentExpression aIdent, final VariableTableEntry aVteBl1) {
		return carrier.getIdent(aIdent, aVteBl1);
	}

	public @NotNull DR_Ident getIdent(final @NotNull IdentTableEntry aIdentTableEntry) {
		return carrier.getIdent(aIdentTableEntry);
	}

	public @NotNull DR_Ident getIdent(final VariableTableEntry aVteBl1) {
		return carrier.getIdent(aVteBl1);
	}

	@Override
	@NotNull
	public String getIdentIAPathNormal(final @NotNull IdentIA ia2) {
		return carrier.getIdentIAPathNormal(ia2);
	}

	@Override
	@NotNull
	public IdentTableEntry getIdentTableEntry(final int index) {
		return carrier.getIdentTableEntry(index);
	}

	@Override
	@Nullable
	public IdentTableEntry getIdentTableEntryFor(final @NotNull IExpression expression) {
		return carrier.getIdentTableEntryFor(expression);
	}

	@Override
	public Instruction getInstruction(final int anIndex) {
		return carrier.getInstruction(anIndex);
	}

	@Override
	public EvaContainerNC getParent() {
		return carrier.getParent();
	}

	public @NotNull DR_ProcCall getProcCall(final IExpression aZ, final ProcTableEntry aPte) {
		return carrier.getProcCall(aZ, aPte);
	}

	@Override
	@NotNull
	public ProcTableEntry getProcTableEntry(final int index) {
		return carrier.getProcTableEntry(index);
	}

	@Override
	@NotNull
	public TypeTableEntry getTypeTableEntry(final int index) {
		return carrier.getTypeTableEntry(index);
	}

	public @NotNull DR_Variable getVar(final VariableStatement aElement) {
		return carrier.getVar(aElement);
	}

	@Override
	@NotNull
	public VariableTableEntry getVarTableEntry(final int index) {
		return carrier.getVarTableEntry(index);
	}

	@Override
	public @NotNull List<Instruction> instructions() {
		return carrier.instructions();
	}

	@Override
	public @NotNull List<Label> labels() {
		return carrier.labels();
	}

	@Override
	@NotNull
	public TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type) {
		return carrier.newTypeTableEntry(type1, type);
	}

	@Override
	@NotNull
	public TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type, final IExpression expression) {
		return carrier.newTypeTableEntry(type1, type, expression);
	}

	@Override
	@NotNull
	public TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type, final IExpression expression, final TableEntryIV aTableEntryIV) {
		return carrier.newTypeTableEntry(type1, type, expression, aTableEntryIV);
	}

	@Override
	@NotNull
	public TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type, final TableEntryIV aTableEntryIV) {
		return carrier.newTypeTableEntry(type1, type, aTableEntryIV);
	}

	@Override
	public int nextTemp() {
		return carrier.nextTemp();
	}

	public void onGenClass(final @NotNull OnGenClass aOnGenClass) {
		carrier.onGenClass(aOnGenClass);
	}

	@Override
	public void place(final @NotNull Label label) {
		carrier.place(label);
	}

	@Override
	public BaseEvaFunction.@NotNull __Reactive reactive() {
		return carrier.reactive();
	}

	@Override
	public IEvaFunctionBase getCarrier() {
		return carrier;
	}

	@Override
	public OS_Module getModule__() {
		return carrier.module();
	}

	@Override
	public WhyNotGarish_Function getWhyNotGarishFunction(final @NotNull GenerateC aGc) {
		return aGc.a_lookup(carrier);
	}

	@Override
	public void resolveTypeDeferred(final @NotNull GenType aType) {
		carrier.resolveTypeDeferred(aType);
	}

	@Override
	public void setClass(@NotNull final EvaNode aNode) {
		carrier.setClass(aNode);
	}

	@Override
	public void setCode(final int aCode) {
		carrier.setCode(aCode);
	}

	@Override
	public void setParent(final EvaContainerNC aGeneratedContainerNC) {
		carrier.setParent(aGeneratedContainerNC);
	}

	@Override
	@NotNull
	public DeferredObject<GenType, Void, Void> typeDeferred() {
		return carrier.typeDeferred();
	}

	@Override
	public Promise<GenType, Void, Void> typePromise() {
		return carrier.typePromise();
	}

	@Override
	public @Nullable InstructionArgument vte_lookup(final String text) {
		return carrier.vte_lookup(text);
	}

	public String identityString() {
		return carrier.identityString();
	}

	public OS_Module module() {
		return carrier.module();
	}

	@Override
	public @NotNull FunctionDef getFD() {
		return carrier.getFD();
	}

	@Override
	@Nullable
	public VariableTableEntry getSelf() {
		return carrier.getSelf();
	}
}
