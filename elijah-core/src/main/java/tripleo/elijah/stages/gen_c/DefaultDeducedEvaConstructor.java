package tripleo.elijah.stages.gen_c;

import io.reactivex.rxjava3.subjects.*;

import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.util.range.Range;

import java.util.*;

public class DefaultDeducedEvaConstructor implements DeducedEvaConstructor {
    private final EvaConstructor carrier;

    public DefaultDeducedEvaConstructor(EvaConstructor aGf) {
        carrier = aGf;
    }

    public void addDependentFunction(@NotNull FunctionInvocation aFunction) {
        carrier.addDependentFunction(aFunction);
    }

    public void addDependentType(@NotNull GenType aType) {
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

    public void noteDependencies(@NotNull Dependency d) {
        carrier.noteDependencies(d);
    }

    public static @NotNull List<InstructionArgument> _getIdentIAPathList(@NotNull InstructionArgument oo) {
        return BaseEvaFunction._getIdentIAPathList(oo);
    }

    public static @NotNull List<DT_Resolvable> _getIdentIAResolvableList(@NotNull InstructionArgument oo) {
        return BaseEvaFunction._getIdentIAResolvableList(oo);
    }

    @NotNull
    public DT_Resolvabley _getIdentIAResolvable(@NotNull IdentIA aIdentIA) {
        return carrier._getIdentIAResolvable(aIdentIA);
    }

    @Override
    public int add(InstructionName aName, List<InstructionArgument> args_, Context ctx) {
        return carrier.add(aName, args_, ctx);
    }

    @Override
    public void addContext(Context context, Range r) {
        carrier.addContext(context, r);
    }

    @Override
    public void addElement(OS_Element aElement, DeduceElement aDeduceElement) {
        carrier.addElement(aElement, aDeduceElement);
    }

    @Override
    public int addIdentTableEntry(IdentExpression ident, Context context) {
        return carrier.addIdentTableEntry(ident, context);
    }

    @Override
    public @NotNull Label addLabel() {
        return carrier.addLabel();
    }

    @Override
    public @NotNull Label addLabel(String base_name, boolean append_int) {
        return carrier.addLabel(base_name, append_int);
    }

    @Override
    public int addVariableTableEntry(String name, VariableTableType vtt, TypeTableEntry type, OS_Element el) {
        return carrier.addVariableTableEntry(name, vtt, type, el);
    }

    public @NotNull DR_Type buildDrTypeFromNonGenericTypeName(TypeName aNonGenericTypeName) {
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
    public @Nullable Label findLabel(int index) {
        return carrier.findLabel(index);
    }

    @Override
    public @NotNull InstructionArgument get_assignment_path(@NotNull IExpression expression, @NotNull GenerateFunctions generateFunctions, @NotNull Context context) {
        return carrier.get_assignment_path(expression, generateFunctions, context);
    }

    @Override
    public int getCode() {
        return carrier.getCode();
    }

    @Override
    @NotNull
    public ConstantTableEntry getConstTableEntry(int index) {
        return carrier.getConstTableEntry(index);
    }

    @Override
    public Context getContextFromPC(int pc) {
        return carrier.getContextFromPC(pc);
    }

    @Override
    public @NotNull Dependency getDependency() {
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

    public @NotNull DR_Ident getIdent(IdentExpression aIdent, VariableTableEntry aVteBl1) {
        return carrier.getIdent(aIdent, aVteBl1);
    }

    public @NotNull DR_Ident getIdent(@NotNull IdentTableEntry aIdentTableEntry) {
        return carrier.getIdent(aIdentTableEntry);
    }

    public @NotNull DR_Ident getIdent(VariableTableEntry aVteBl1) {
        return carrier.getIdent(aVteBl1);
    }

    @Override
    @NotNull
    public String getIdentIAPathNormal(@NotNull IdentIA ia2) {
        return carrier.getIdentIAPathNormal(ia2);
    }

    @Override
    @NotNull
    public IdentTableEntry getIdentTableEntry(int index) {
        return carrier.getIdentTableEntry(index);
    }

    @Override
    @Nullable
    public IdentTableEntry getIdentTableEntryFor(@NotNull IExpression expression) {
        return carrier.getIdentTableEntryFor(expression);
    }

    @Override
    public Instruction getInstruction(int anIndex) {
        return carrier.getInstruction(anIndex);
    }

    @Override
    public EvaContainerNC getParent() {
        return carrier.getParent();
    }

    public @NotNull DR_ProcCall getProcCall(IExpression aZ, ProcTableEntry aPte) {
        return carrier.getProcCall(aZ, aPte);
    }

    @Override
    @NotNull
    public ProcTableEntry getProcTableEntry(int index) {
        return carrier.getProcTableEntry(index);
    }

    @Override
    @NotNull
    public TypeTableEntry getTypeTableEntry(int index) {
        return carrier.getTypeTableEntry(index);
    }

    public @NotNull DR_Variable getVar(VariableStatement aElement) {
        return carrier.getVar(aElement);
    }

    @Override
    @NotNull
    public VariableTableEntry getVarTableEntry(int index) {
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
    public TypeTableEntry newTypeTableEntry(TypeTableEntry.@NotNull Type type1, OS_Type type) {
        return carrier.newTypeTableEntry(type1, type);
    }

    @Override
    @NotNull
    public TypeTableEntry newTypeTableEntry(TypeTableEntry.@NotNull Type type1, OS_Type type, IExpression expression) {
        return carrier.newTypeTableEntry(type1, type, expression);
    }

    @Override
    @NotNull
    public TypeTableEntry newTypeTableEntry(TypeTableEntry.@NotNull Type type1, OS_Type type, IExpression expression, TableEntryIV aTableEntryIV) {
        return carrier.newTypeTableEntry(type1, type, expression, aTableEntryIV);
    }

    @Override
    @NotNull
    public TypeTableEntry newTypeTableEntry(TypeTableEntry.@NotNull Type type1, OS_Type type, TableEntryIV aTableEntryIV) {
        return carrier.newTypeTableEntry(type1, type, aTableEntryIV);
    }

    @Override
    public int nextTemp() {
        return carrier.nextTemp();
    }

    public void onGenClass(@NotNull OnGenClass aOnGenClass) {
        carrier.onGenClass(aOnGenClass);
    }

    @Override
    public void place(@NotNull Label label) {
        carrier.place(label);
    }

//    @Override
//    public BaseEvaFunction.@NotNull __Reactive reactive() {
//        return carrier.reactive();
//    }

    @Override
    public void resolveTypeDeferred(@NotNull GenType aType) {
        carrier.resolveTypeDeferred(aType);
    }

    @Override
    public void setClass(@NotNull EvaNode aNode) {
        carrier.setClass(aNode);
    }

    @Override
    public void setCode(int aCode) {
        carrier.setCode(aCode);
    }

    @Override
    public void setParent(EvaContainerNC aGeneratedContainerNC) {
        carrier.setParent(aGeneratedContainerNC);
    }

    @Override
    public Eventual<GenType> typeDeferred() {
        return carrier.typeDeferred();
    }

    @Override
    public Eventual<GenType> typePromise() {
        return carrier.typePromise();
    }

    @Override
    public @Nullable InstructionArgument vte_lookup(String text) {
        return carrier.vte_lookup(text);
    }

    public Eventual<DeduceElement3_Constructor> de3_Promise() {
        return carrier.de3_Promise();
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

    public String identityString() {
        return carrier.identityString();
    }

    public @NotNull OS_Module module() {
        return carrier.module();
    }

    public String name() {
        return carrier.name();
    }

    public void setFunctionInvocation(@NotNull FunctionInvocation fi) {
        carrier.setFunctionInvocation(fi);
    }

    @Override
    public String toString() {
        return carrier.toString();
    }

    @Override
    public IEvaConstructor.BaseEvaConstructor_Reactive reactive() {
        return (IEvaConstructor.BaseEvaConstructor_Reactive) carrier.reactive();
    }

    @Override
    public EvaConstructor getCarrier() {
        return carrier;
    }
}
