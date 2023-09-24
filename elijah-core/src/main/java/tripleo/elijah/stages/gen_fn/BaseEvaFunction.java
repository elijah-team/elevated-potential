/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.DefaultReactive;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.gen_generic.Dependency;
import tripleo.elijah.stages.gen_generic.IDependencyReferent;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Holder;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.world.WorldGlobals;
import tripleo.elijah.world.impl.DefaultLivingFunction;
import tripleo.util.range.Range;

import java.util.*;
import java.util.function.Consumer;

import static tripleo.elijah.stages.deduce.DeduceTypes2.to_int;

/**
 * Created 9/10/20 2:57 PM
 */
public abstract class BaseEvaFunction extends AbstractDependencyTracker implements EvaNode, DeduceTypes2.ExpectationBase, IDependencyReferent, IEvaFunctionBase {
	public class __Reactive extends DefaultReactive {
		@Override
		public <T> void addListener(final Consumer<T> t) {
			int y = 2;
		}
	}
	public static @NotNull List<InstructionArgument> _getIdentIAPathList(@NotNull InstructionArgument oo) {
		LinkedList<InstructionArgument> s = new LinkedList<InstructionArgument>();
		while (oo != null) {
			if (oo instanceof IntegerIA) {
				s.addFirst(oo);
				oo = null;
			} else if (oo instanceof IdentIA) {
				final IdentTableEntry ite1 = ((IdentIA) oo).getEntry();
				s.addFirst(oo);
				oo = ite1.getBacklink();
			} else if (oo instanceof ProcIA) {
				s.addFirst(oo);
				oo = null;
			} else
				throw new IllegalStateException("Invalid InstructionArgument");
		}
		return s;
	}

	public static @NotNull List<DT_Resolvable> _getIdentIAResolvableList(@NotNull InstructionArgument oo) {
		LinkedList<DT_Resolvable> R = new LinkedList<>();
		while (oo != null) {
			if (oo instanceof IntegerIA integerIA) {
				var vte = integerIA.getEntry();

				if (vte._vs == null) {
					final OS_Element[] el = {null};
					vte._p_elementPromise.then(el1 -> el[0] = el1);

					assert el[0] != null;

					R.addFirst(DT_Resolvable.from(oo, el[0], null));
				} else {
					R.addFirst(DT_Resolvable.from(oo, vte._vs, null));
				}
				oo = null;
			} else if (oo instanceof final IdentIA identIA) {
				final IdentTableEntry ite1 = identIA.getEntry();

				final OS_Element[] el = {null};
				ite1._p_resolvedElementPromise.then(el1 -> el[0] = el1);

				//assert el[0] != null;

				FunctionInvocation cfi = null;
				if (ite1._callable_pte() != null) {
					var cpte = ite1._callable_pte();
					if (cpte.getFunctionInvocation() != null) {
						cfi = cpte.getFunctionInvocation();
					}
				}

				//assert cfi != null;
				// ^^ fails for folders.forEach

				R.addFirst(DT_Resolvable.from(oo, el[0], cfi));
				oo = ite1.getBacklink();
			} else if (oo instanceof ProcIA procIA) {
				var pte = procIA.getEntry();
				assert pte != null;

				final OS_Element[] el = {null};
				pte._p_elementPromise.then(el1 -> el[0] = el1);

				assert el[0] != null;

				FunctionInvocation cfi = null;
				if (pte.getFunctionInvocation() != null) {
					cfi = pte.getFunctionInvocation();
				}

				assert cfi != null;

				R.addFirst(DT_Resolvable.from(oo, el[0], cfi));
				oo = null;
			} else
				throw new IllegalStateException("Invalid InstructionArgument");
		}
		return R;
	}
	static void printTables(@NotNull EvaFunction gf) {
		tripleo.elijah.util.Stupidity.println_out_2("VariableTable ");
		for (VariableTableEntry variableTableEntry : gf.vte_list) {
			tripleo.elijah.util.Stupidity.println_out_2("\t" + variableTableEntry);
		}
		tripleo.elijah.util.Stupidity.println_out_2("ConstantTable ");
		for (ConstantTableEntry constantTableEntry : gf.cte_list) {
			tripleo.elijah.util.Stupidity.println_out_2("\t" + constantTableEntry);
		}
		tripleo.elijah.util.Stupidity.println_out_2("ProcTable     ");
		for (ProcTableEntry procTableEntry : gf.prte_list) {
			tripleo.elijah.util.Stupidity.println_out_2("\t" + procTableEntry);
		}
		tripleo.elijah.util.Stupidity.println_out_2("TypeTable     ");
		for (TypeTableEntry typeTableEntry : gf.tte_list) {
			tripleo.elijah.util.Stupidity.println_out_2("\t" + typeTableEntry);
		}
		tripleo.elijah.util.Stupidity.println_out_2("IdentTable    ");
		for (IdentTableEntry identTableEntry : gf.idte_list) {
			tripleo.elijah.util.Stupidity.println_out_2("\t" + identTableEntry);
		}
	}
	private final DeferredObject<EvaClass, Void, Void> _p_assignEvaClass = new DeferredObject<>();
	private final Dependency                           dependency        = new Dependency(this);
	public @NotNull List<DR_Item>                       drs               = new ArrayList<>();
	private final   List<Label>                         labelList         = new ArrayList<Label>();
	//
	// region INSTRUCTIONS
	//
	private final   DeferredObject<GenType, Void, Void> _p_assignGenType  = new DeferredObject<GenType, Void, Void>();
	public          DefaultLivingFunction               _living;
	public @NotNull List<ConstantTableEntry>            cte_list          = new ArrayList<ConstantTableEntry>();
	public          boolean                             deducedAlready;
	public @NotNull List<Integer>                       deferred_calls    = new ArrayList<Integer>();
	public          FunctionInvocation                  fi;
	public @NotNull List<IdentTableEntry>               idte_list         = new ArrayList<IdentTableEntry>();
	public @NotNull List<Instruction>                   instructionsList  = new ArrayList<Instruction>();
	public @NotNull List<ProcTableEntry>                prte_list         = new ArrayList<ProcTableEntry>();
	public @NotNull List<TypeTableEntry>                tte_list          = new ArrayList<TypeTableEntry>();
	public @NotNull List<VariableTableEntry>            vte_list          = new ArrayList<VariableTableEntry>();
	@NotNull        Map<OS_Element, DeduceElement>      elements          = new HashMap<OS_Element, DeduceElement>();
	private         int                                 _nextTemp         = 0;
	private         __Reactive                          _reactive;
	private         int                                 code              = 0;

	private         EvaNode                             genClass;

	// endregion

	//
	// region LABELS
	//

	private         int                                 instruction_index = 0;

	private         int                                 label_count       = 0;

	// endregion
	//
	// region Ident-IA
	//
	private         EvaContainerNC                      parent;

	public @NotNull DT_Resolvabley _getIdentIAResolvable(final @NotNull IdentIA aIdentIA) {
		var x = _getIdentIAResolvableList(aIdentIA);

		return new DT_Resolvabley(x);
	}

	// endregion

	//
	// region get-entries
	//

	@Override
	public int add(final InstructionName aName, final List<InstructionArgument> args_, final Context ctx) {
		final Instruction i = new Instruction();
		i.setIndex(instruction_index);
		instruction_index++;
		i.setName(aName);
		i.setArgs(args_);
		i.setContext(ctx);
		instructionsList.add(i);
		return i.getIndex();
	}

	@Override
	public void addContext(final Context context, final Range r) {
//		contextToRangeMap.put(r, context);
	}

	@Override
	public void addElement(final OS_Element aElement, final DeduceElement aDeduceElement) {
		elements.put(aElement, aDeduceElement);
	}

	@Override
	public int addIdentTableEntry(final IdentExpression ident, final Context context) {
		for (int i = 0; i < idte_list.size(); i++) {
			if (idte_list.get(i).getIdent() == ident && idte_list.get(i).getPC() == context)
				return i;
		}
		final IdentTableEntry idte = new IdentTableEntry(idte_list.size(), ident, context, this);

		idte.set_ident(getIdent(idte));

		idte_list.add(idte);
		return idte.getIndex();
	}

	@Override
	public @NotNull Label addLabel() {
		return addLabel("__label", true);
	}

	// endregion

	@Override
	public @NotNull Label addLabel(final String base_name, final boolean append_int) {
		final Label  label = new Label(this);
		final String name;
		if (append_int) {
			label.setNumber(label_count);
			name = String.format("%s%d", base_name, label_count);
			label_count++;
		} else {
			name = base_name;
			label.setNumber(label_count);
		}
		label.setName(name);
		labelList.add(label);
		return label;
	}

	@Override
	public int addVariableTableEntry(final String name, final VariableTableType vtt, final TypeTableEntry type, OS_Element el) {
		final VariableTableEntry vte = new VariableTableEntry(vte_list.size(), vtt, name, type, el);
		vte_list.add(vte);
		return vte.getIndex();
	}

	public @NotNull DR_Type buildDrTypeFromNonGenericTypeName(final TypeName aNonGenericTypeName) {
		var r = new DR_Type(this, aNonGenericTypeName);
		r.build();
		return r;
	}

	public Map<OS_Element, DeduceElement> elements() {
		return elements;
	}

	@Override
	public String expectationString() {
		return toString();
	}

	@Override
	public @Nullable Label findLabel(final int index) {
		for (final Label label : labelList) {
			if (label.getIndex() == index)
				return label;
		}
		return null;
	}

//	Map<Range, ContextImpl> contextToRangeMap = new HashMap<Range, ContextImpl>();

	@Override
	public @NotNull InstructionArgument get_assignment_path(@NotNull final IExpression expression,
															@NotNull final GenerateFunctions generateFunctions,
															@NotNull Context context) {
		switch (expression.getKind()) {
		case DOT_EXP: {
			final DotExpression       de        = (DotExpression) expression;
			final InstructionArgument left_part = get_assignment_path(de.getLeft(), generateFunctions, context);
			return get_assignment_path(left_part, de.getRight(), generateFunctions, context);
		}
		case QIDENT:
			throw new NotImplementedException();
		case PROCEDURE_CALL: {
			ProcedureCallExpression pce = (ProcedureCallExpression) expression;
			if (pce.getLeft() instanceof final @NotNull IdentExpression identExpression) {
				int                        idte_index = addIdentTableEntry(identExpression, identExpression.getContext());
				final IdentIA              identIA    = new IdentIA(idte_index, this);
				final List<TypeTableEntry> args_types = generateFunctions.get_args_types(pce.getArgs(), (@NotNull EvaFunction) this, context);
				int                        i          = generateFunctions.addProcTableEntry(pce, identIA, args_types, this);
				return new ProcIA(i, this);
			}
			return get_assignment_path(pce.getLeft(), generateFunctions, context); // TODO this looks wrong. what are we supposed to be doing here?
		}
		case GET_ITEM:
			throw new NotImplementedException();
		case IDENT: {
			final IdentExpression     ie     = (IdentExpression) expression;
			final String              text   = ie.getText();
			final InstructionArgument lookup = vte_lookup(text); // IntegerIA(variable) or ConstTableIA or null
			if (lookup != null)
				return lookup;
			final int ite = addIdentTableEntry(ie, context);
			return new IdentIA(ite, this);
		}
		default:
			throw new IllegalStateException("Unexpected value: " + expression.getKind());
		}
	}

	private @NotNull InstructionArgument get_assignment_path(@NotNull final InstructionArgument prev,
															 @NotNull final IExpression expression,
															 @NotNull final GenerateFunctions generateFunctions,
															 @NotNull final Context context) {
		switch (expression.getKind()) {
		case DOT_EXP: {
			final DotExpression       de        = (DotExpression) expression;
			final InstructionArgument left_part = get_assignment_path(de.getLeft(), generateFunctions, context);
			if (left_part instanceof IdentIA) {
				((IdentIA) left_part).setPrev(prev);
//				getIdentTableEntry(to_int(left_part)).addStatusListener(new DeduceTypes2.FoundParent());
			} else
				throw new NotImplementedException();
			return get_assignment_path(left_part, de.getRight(), generateFunctions, context);
		}
		case QIDENT:
			throw new NotImplementedException();
		case PROCEDURE_CALL:
			throw new NotImplementedException();
		case GET_ITEM:
			throw new NotImplementedException();
		case IDENT: {
			final IdentExpression ie      = (IdentExpression) expression;
			final int             ite     = addIdentTableEntry(ie, context);
			final IdentIA         identIA = new IdentIA(ite, this);
			identIA.setPrev(prev);
//			getIdentTableEntry(ite).addStatusListener(new DeduceTypes2.FoundParent()); // inject!
			return identIA;
		}
		default:
			throw new IllegalStateException("Unexpected value: " + expression.getKind());
		}
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	@NotNull
	public ConstantTableEntry getConstTableEntry(final int index) {
		return cte_list.get(index);
	}

	@Override
	public Context getContextFromPC(final int pc) {
//		for (Map.Entry<Range, ContextImpl> rangeContextEntry : contextToRangeMap.entrySet()) {
//			if (rangeContextEntry.getKey().has(pc))
//				return rangeContextEntry.getValue();
//		}
//		return null;
		return instructionsList.get(pc).getContext();
	}

	@Override
	public @NotNull Dependency getDependency() {
		return dependency;
	}

	@Override
	public @NotNull String getFunctionName() {
		// TODO change to abstract with override??
		if (this instanceof EvaConstructor) {
			int                   y               = 2;
			final IdentExpression constructorName = this.getFD().getNameNode();
			final String          constructorNameText;
			if (constructorName == WorldGlobals.emptyConstructorName) {
				constructorNameText = "";
			} else {
				constructorNameText = constructorName.getText();
			}
			return constructorNameText;
		} else {
			return getFD().getNameNode().getText();
		}
	}

	@Override
	public EvaNode getGenClass() {
		return genClass;
	}

	public @NotNull DR_Ident getIdent(final IdentExpression aIdent, final VariableTableEntry aVteBl1) {
		final DR_Ident e = DR_Ident.create(aIdent, aVteBl1, this);
		drs.add(e);
		return e;
	}

	public @NotNull DR_Ident getIdent(final @NotNull IdentTableEntry aIdentTableEntry) {
		final DR_Ident e = DR_Ident.create(aIdentTableEntry, this);
		drs.add(e);
		return e;
	}

	public @NotNull DR_Ident getIdent(final VariableTableEntry aVteBl1) {
		final DR_Ident e = DR_Ident.create(aVteBl1, this);
		drs.add(e);
		return e;
	}

	/**
	 * Returns a string that represents the path encoded by ia2.
	 * Does not transform the string into target language (ie C).
	 * Called from {@link DeduceTypes2#do_assign_call(BaseEvaFunction, Context, IdentTableEntry, FnCallArgs, int)}
	 * or {@link DeduceTypes2#deduce_generated_function(EvaFunction, ModuleThing)}
	 * or {@link DeduceTypes2#resolveIdentIA_(Context, IdentIA, BaseEvaFunction, FoundElement)}
	 *
	 * @param ia2 the path
	 * @return a string that represents the path encoded by ia2
	 */
	@Override
	public @NotNull String getIdentIAPathNormal(final @NotNull IdentIA ia2) {
		final List<InstructionArgument> s = _getIdentIAPathList(ia2);

		//
		// TODO NOT LOOKING UP THINGS, IE PROPERTIES, MEMBERS
		//
		List<String> sl = new ArrayList<String>();
		for (final InstructionArgument ia : s) {
			final String text;
			if (ia instanceof IntegerIA) {
				final VariableTableEntry vte = getVarTableEntry(to_int(ia));
				text = vte.getName();
			} else if (ia instanceof IdentIA) {
				final IdentTableEntry idte = getIdentTableEntry(((IdentIA) ia).getIndex());
				text = idte.getIdent().getText();
			} else if (ia instanceof ProcIA) {
				final ProcTableEntry prte = getProcTableEntry(to_int(ia));
				assert prte.__debug_expression instanceof ProcedureCallExpression;
				text = ((ProcedureCallExpression) prte.__debug_expression).printableString();
			} else
				throw new NotImplementedException();
			sl.add(text);
		}
		return Helpers.String_join(".", sl);
	}

	@Override
	@NotNull
	public IdentTableEntry getIdentTableEntry(final int index) {
		return idte_list.get(index);
	}

	/**
	 * Returns first {@link IdentTableEntry} that matches expression
	 * Only works for IdentExpressions
	 *
	 * @param expression {@link IdentExpression} to test for
	 * @return IdentTableEntry or null
	 */
	@Override
	public @Nullable IdentTableEntry getIdentTableEntryFor(@NotNull IExpression expression) {
		for (IdentTableEntry identTableEntry : idte_list) {
			// TODO make this work for Qualidents and DotExpressions
			if (identTableEntry.getIdent().getText().equals(((IdentExpression) expression).getText()) && identTableEntry.getBacklink() == null) {
				return identTableEntry;
			}
		}
		return null;
	}

	@Override
	public Instruction getInstruction(final int anIndex) {
		return instructionsList.get(anIndex);
	}

	@Override
	public EvaContainerNC getParent() {
		return parent;
	}

	public @NotNull DR_ProcCall getProcCall(final IExpression aZ, final ProcTableEntry aPte) {
		DR_ProcCall e = new DR_ProcCall(aZ, aPte, this);
		drs.add(e);
		return e;
	}

	@Override
	@NotNull
	public ProcTableEntry getProcTableEntry(final int index) {
		return prte_list.get(index);
	}

	@Override
	@NotNull
	public TypeTableEntry getTypeTableEntry(final int index) {
		return tte_list.get(index);
	}

	public @NotNull DR_Variable getVar(final VariableStatement aElement) {
		DR_Variable e = new DR_Variable(aElement, this);
		drs.add(e);
		return e;
	}

	@Override
	@NotNull
	public VariableTableEntry getVarTableEntry(final int index) {
		return vte_list.get(index);
	}

	@Override
	public @NotNull List<Instruction> instructions() {
		return instructionsList;
	}

	@Override
	public @NotNull List<Label> labels() {
		return labelList;
	}

	@Override
	public @NotNull TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type) {
		return newTypeTableEntry(type1, type, null, null);
	}

	@Override
	public @NotNull TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type, final IExpression expression) {
		return newTypeTableEntry(type1, type, expression, null);
	}

	@Override
	public @NotNull TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type, final IExpression expression, TableEntryIV aTableEntryIV) {
		final TypeTableEntry typeTableEntry = new TypeTableEntry(tte_list.size(), type1, type, expression, aTableEntryIV);
		typeTableEntry.setAttached(type); // README make sure tio call callback
		tte_list.add(typeTableEntry);
		return typeTableEntry;
	}

	@Override
	public @NotNull TypeTableEntry newTypeTableEntry(final TypeTableEntry.@NotNull Type type1, final OS_Type type, TableEntryIV aTableEntryIV) {
		return newTypeTableEntry(type1, type, null, aTableEntryIV);
	}

	@Override
	public int nextTemp() {
		++_nextTemp;
		return _nextTemp;
	}

	/*
	 * Hook in for GeneratedClass
	 */
	public void onGenClass(final @NotNull OnGenClass aOnGenClass) {
		_p_assignEvaClass.then(aOnGenClass::accept);
	}

	@Override
	public void place(@NotNull final Label label) {
		label.setIndex(instruction_index);
	}

	public @NotNull __Reactive reactive() {
		if (_reactive == null)
			_reactive = new __Reactive();
		return _reactive;
	}

	@Override
	public void resolveTypeDeferred(final @NotNull GenType aType) {
		if (_p_assignGenType.isPending())
			_p_assignGenType.resolve(aType);
		else {
			final Holder<GenType> holder = new Holder<GenType>();
			_p_assignGenType.then(new DoneCallback<GenType>() {
				@Override
				public void onDone(final GenType result) {
					holder.set(result);
				}
			});
			tripleo.elijah.util.Stupidity.println_err_2(String.format("Trying to resolve function twice 1) %s 2) %s", holder.get().asString(), aType.asString()));
		}
	}

	@Override
	public void setClass(@NotNull EvaNode aNode) {
		assert aNode instanceof EvaClass || aNode instanceof EvaNamespace;
		genClass = aNode;

		if (aNode instanceof EvaClass) {
			_p_assignEvaClass.resolve((EvaClass) aNode);
		} else {
			throw new IllegalArgumentException("aNode is not EvaClass");
		}
	}

	@Override
	public void setCode(int aCode) {
		code = aCode;
	}

	@Override
	public void setParent(EvaContainerNC aGeneratedContainerNC) {
		parent = aGeneratedContainerNC;
	}

	@Override
	public @NotNull DeferredObject<GenType, Void, Void> typeDeferred() {
		return _p_assignGenType;
	}

	@Override
	public Promise<GenType, Void, Void> typePromise() {
		return _p_assignGenType.promise();
	}

	/**
	 * @param text variable name from the source file
	 * @return {@link IntegerIA} or {@link ConstTableIA} or null if not found, meaning not a local variable
	 */
	@Override
	public @Nullable InstructionArgument vte_lookup(final String text) {
		int index = 0;
		for (final VariableTableEntry variableTableEntry : vte_list) {
			final String variableTableEntryName = variableTableEntry.getName();
			if (variableTableEntryName != null) // null when temp
				if (variableTableEntryName.equals(text))
					return new IntegerIA(index, this);
			index++;
		}
		index = 0;
		for (final ConstantTableEntry constTableEntry : cte_list) {
			final String constTableEntryName = constTableEntry.getName();
			if (constTableEntryName != null) // null when not assigned
				if (constTableEntryName.equals(text))
					return new ConstTableIA(index, this);
			index++;
		}
		return null;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
