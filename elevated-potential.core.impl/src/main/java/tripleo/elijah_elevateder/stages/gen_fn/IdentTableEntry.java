/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.gen_fn;

//import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.DefaultReactive;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created 9/12/20 10:27 PM
 */
public class IdentTableEntry extends BaseTableEntry1
		implements Constructable, TableEntryIV, DeduceTypes2.ExpectationBase, IDeduceResolvable {
	private class __StatusListener__ITE_SetResolvedElement implements StatusListener {
		@Override
		public void onChange(@NotNull IElementHolder eh, Status newStatus) {
			if (newStatus == Status.KNOWN) {
				setResolvedElement(eh.getElement());
			}
		}
	}

	public class _Reactive_IDTE extends DefaultReactive {
		@Override
		public <IdentTableEntry> void addListener(final Consumer<IdentTableEntry> t) {
			throw new IllegalStateException("Error");
		}

		public <IdentTableEntry> void addResolveListener(final @NotNull Consumer<IdentTableEntry> t) {
			_p_resolveListenersPromise
					.then((DoneCallback<? super tripleo.elijah_elevateder.stages.gen_fn.IdentTableEntry>) result -> t
							.accept((IdentTableEntry) result));
		}
	}

	public static final class ITE_Resolver_Result {
		private final OS_Element element;

		public ITE_Resolver_Result(OS_Element element) {
			this.element = element;
		}

		public OS_Element element() {
			return element;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (ITE_Resolver_Result) obj;
			return Objects.equals(this.element, that.element);
		}

		@Override
		public int hashCode() {
			return Objects.hash(element);
		}

		@Override
		public String toString() {
			return "ITE_Resolver_Result[" +
					"element=" + element + ']';
		}

		}

	public final DeferredObject<OS_Element, ResolveError, Void>   _p_resolvedElementPromise = new DeferredObject<>();
	private final DeferredObject<InstructionArgument, Void, Void> _p_backlinkSet            = new DeferredObject<InstructionArgument, Void, Void>();
	private final DeferredObject<ProcTableEntry, Void, Void>      _p_constructableDeferred  = new DeferredObject<>();
	private final DeferredObject<GenType, Void, Void> _p_fefiDone = new DeferredObject<GenType, Void, Void>();
	private final          DeferredObject<IdentTableEntry, Void, Void> _p_resolveListenersPromise = new DeferredObject<>();
	private final          Resolve_Ident_IA.DeduceElementIdent         dei                        = new Resolve_Ident_IA.DeduceElementIdent(this);
	private final @NotNull EvaExpression<IdentExpression>              ident;
	private final int index;
	private final Context context;
	private final BaseEvaFunction _definedFunction;
	public EvaNode externalRef;
	private EvaNode resolvedType;
	public ProcTableEntry constructable_pte;
	public boolean fefi = false;
	public @NotNull Map<Integer, TypeTableEntry> potentialTypes = new HashMap<Integer, TypeTableEntry>();
	public  boolean                    preUpdateStatusListenerAdded;
	public  PromiseExpectation<String> resolveExpectation;
	public  TypeTableEntry             type;
	private DR_Ident                   _ident;
	InstructionArgument backlink;
	boolean insideGetResolvedElement = false;

	private DeduceElement3_IdentTableEntry _de3;

	private _Reactive_IDTE _reactive;

	private ITE_Resolver_Result _resolver_result;

	private final Eventual<_Reactive_IDTE> _reactiveEventual = new Eventual<>();

	private final Eventual<EvaNode> _onExternalRef = new Eventual<>();

	private final List<ITE_Resolver> resolvers = new ArrayList<>();

	public VariableStatement _cheat_variableStatement; // DTR_VariableStatement 07/30

	public IdentTableEntry(final int index, final IdentExpression ident, Context context,
			final BaseEvaFunction aBaseEvaFunction) {
		this.index = index;
		this.ident = new EvaExpression<>(ident, this);
		this.context = context;

		this._definedFunction = aBaseEvaFunction;

		addStatusListener(new __StatusListener__ITE_SetResolvedElement());
		setupResolve();

		_p_resolvedElementPromise.then(this::resolveLanguageLevelConstruct);
		typeResolve.typeResolution().then(gt -> {
			if (type != null && type.genType != null) // !! 07/30
				type.genType.copy(gt);
		});
	}

	public @NotNull BaseEvaFunction _generatedFunction() {
		return __gf;
	}

	public void addPotentialType(final int instructionIndex, final TypeTableEntry tte) {
		potentialTypes.put(instructionIndex, tte);
	}

	public void addResolver(ITE_Resolver aResolver) {
		resolvers.add(aResolver);
	}

	public Promise<InstructionArgument, Void, Void> backlinkSet() {
		return _p_backlinkSet.promise();
	}

	public @NotNull DeducePath buildDeducePath(BaseEvaFunction generatedFunction) {
		@NotNull
		List<InstructionArgument> x = BaseEvaFunction._getIdentIAPathList(new IdentIA(index, generatedFunction));
		return new DeducePath(this, x);
	}

	public void calculateResolvedElement() {
		var resolved_element = dei.getResolvedElement();
		if (resolved_element != null) {
			if (!_p_resolvedElementPromise.isResolved()) {
				_p_resolvedElementPromise.resolve(resolved_element);
			}
		} else {
			// 08/13 tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("283283 resolution failure for " +
			// dei.getIdentTableEntry().getIdent().getText());
		}
	}

	@Override
	public Promise<ProcTableEntry, Void, Void> constructablePromise() {
		return _p_constructableDeferred.promise();
	}

	public EvaExpression<IdentExpression> evaExpression() {
		return ident;
	}

	@Override
	public @NotNull String expectationString() {
		return "IdentTableEntry{" + "index=" + index + ", ident=" + ident.get() + ", backlink=" + backlink + "}";
	}

	public EvaNode externalRef() {
		return externalRef;
	}

	public void fefiDone(final GenType aGenType) {
		if (_p_fefiDone.isPending())
			_p_fefiDone.resolve(aGenType);
	}

	public DR_Ident get_ident() {
		return _ident;
	}

	/**
	 * Either an {@link IntegerIA} which is a vte or a {@link IdentIA} which is an
	 * idte
	 */
	public InstructionArgument getBacklink() {
		return backlink;
	}

	public @NotNull Resolve_Ident_IA.DeduceElementIdent getDeduceElement() {
		return dei;
	}

	public DeduceElement3_IdentTableEntry getDeduceElement3() {
		return getDeduceElement3(this._deduceTypes2(), __gf);
	}

	public @NotNull DeduceElement3_IdentTableEntry getDeduceElement3(DeduceTypes2 aDeduceTypes2,
			BaseEvaFunction aGeneratedFunction) {
		if (_de3 == null) {
			_de3 = new DeduceElement3_IdentTableEntry(this);
			_de3.deduceTypes2 = aDeduceTypes2;
			_de3.generatedFunction = aGeneratedFunction;
		}
		return _de3;
	}

	public DR_Ident getDefinedIdent() {
		return _definedFunction.getIdent(this);
	}

	public IdentExpression getIdent() {
		return ident.get();
	}

	public int getIndex() {
		return index;
	}

	public Context getPC() {
		return context;
	}

	public Eventual<_Reactive_IDTE> getReactiveEventual() {
		return _reactiveEventual;
	}

	@Override
	public @Nullable OS_Element getResolvedElement() {
		OS_Element resolved_element;

		if (_p_elementPromise.isResolved()) {
			final OS_Element[] r = new OS_Element[1];
			_p_elementPromise.then(x -> r[0] = x);
			return r[0];
		}

		if (insideGetResolvedElement)
			return null;
		insideGetResolvedElement = true;
		resolved_element = dei.getResolvedElement();
		if (resolved_element != null) {
			if (_p_elementPromise.isResolved()) {
				NotImplementedException.raise();
			} else {
				_p_elementPromise.resolve(resolved_element);
			}
		}
		insideGetResolvedElement = false;
		return resolved_element;
	}

	public boolean hasResolvedElement() {
		return !_p_elementPromise.isPending();
	}

	public boolean isResolved() {
		return resolvedType != null;
	}

	public void makeType(final @NotNull BaseEvaFunction aGeneratedFunction, final TypeTableEntry.@NotNull Type aType,
			final IExpression aExpression) {
		type = aGeneratedFunction.newTypeTableEntry(aType, null, aExpression, this);
	}

	public void makeType(final @NotNull BaseEvaFunction aGeneratedFunction, final TypeTableEntry.@NotNull Type aType,
			final OS_Type aOS_Type) {
		type = aGeneratedFunction.newTypeTableEntry(aType, aOS_Type, getIdent(), this);
	}

	public void onExternalRef(final DoneCallback<EvaNode> cb) {
		_onExternalRef.then(cb);
	}

	public void onFefiDone(DoneCallback<GenType> aCallback) {
		_p_fefiDone.then(aCallback);
	}

	public void onResolvedElement(final DoneCallback<OS_Element> cb) {
		_p_resolvedElementPromise.then((cb));
	}

	public void onType(@NotNull DeducePhase phase, OnType callback) {
		phase.onType(this, callback);
	}

	// @SuppressFBWarnings("NP_NONNULL_RETURN_VIOLATION")
	public @NotNull Collection<TypeTableEntry> potentialTypes() {
		return potentialTypes.values();
	}

	public _Reactive_IDTE reactive() {
		if (_reactive == null) {
			_reactive = new _Reactive_IDTE();
		}
		if (_de3 != null) {
			var ce = _de3.deduceTypes2()._ce();
			ce.reactiveJoin(_reactive);
		}

		if (!_reactiveEventual.isResolved()) {
			// TODO !!
			_reactiveEventual.resolve(_reactive);
		}

		return _reactive;
	}

	public EvaNode resolvedType() {
		return resolvedType;
	}

	private void resolveLanguageLevelConstruct(OS_Element element) {
		assert __gf != null;
		assert this._deduceTypes2() != null;

		if (element instanceof FunctionDef fd) {
			NotImplementedException.raise();
		}

		_p_elementPromise.then(x -> {
			NotImplementedException.raise();
		});
	}

	public void resolvers_round() {
		if (_resolver_result != null)
			return;

		for (ITE_Resolver resolver : resolvers) {
			if (!resolver.isDone()) {
				resolver.check();
			}

			if (resolver.isDone()) {
				_resolver_result = resolver.getResult(); // TODO resolve here?? 07/20
				break;
			}

			// ... ??
		}
	}

	@Override
	public void resolveTypeToClass(EvaNode gn) {
		resolvedType = gn;
		assert (type != null);
		if (type != null) // TODO maybe find a more robust solution to this, like another Promise? or just
							// setType? or onPossiblesResolve?
			type.resolve(gn); // TODO maybe this obviates the above?
		if (!_p_resolveListenersPromise.isResolved()) // FIXME 06/16
			_p_resolveListenersPromise.resolve(this);
	}

	public void set_ident(DR_Ident a_ident) {
		_ident = a_ident;
	}

	public void setBacklink(InstructionArgument aBacklink) {
		backlink = aBacklink;
		_p_backlinkSet.resolve(backlink);
	}

	@Override
	public void setConstructable(ProcTableEntry aPte) {
		constructable_pte = aPte;
		if (_p_constructableDeferred.isPending())
			_p_constructableDeferred.resolve(constructable_pte);
		else {
			// final Holder<ProcTableEntry> holder = new Holder<ProcTableEntry>();
			_p_constructableDeferred.then(el -> {
				SimplePrintLoggerToRemoveSoon
						.println_err_2(String.format("Setting constructable_pte twice 1) %s and 2) %s", el, aPte));
				// holder.set(el);
			});
		}
	}

	public void setDeduceTypes2(final @NotNull DeduceTypes2 aDeduceTypes2, final Context aContext,
			final @NotNull BaseEvaFunction aGeneratedFunction) {
		dei.setDeduceTypes2(aDeduceTypes2, aContext, aGeneratedFunction);
	}

	public void setExternalRef(final EvaNode aResult) {
		externalRef = aResult;
		_onExternalRef.resolve(aResult);
	}

	@Override
	public void setGenType(GenType aGenType) {
		if (type != null) {
			type.genType.copy(aGenType);
		} else {
			throw new IllegalStateException("idte-102 Attempting to set a null type");
//			tripleo.elijah.util.Stupidity.println_err_2("idte-102 Attempting to set a null type");
		}
	}

	@Override
	public @NotNull String toString() {
		return "IdentTableEntry{" + "index=" + index + ", ident=" + ident.get() + ", backlink=" + backlink + ", status="
				+ status + ", resolved=" + resolvedType + ", potentialTypes=" + potentialTypes + ", type=" + type + '}';
	}
}
//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
