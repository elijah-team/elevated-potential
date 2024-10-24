package tripleo.elijah_elevateder.stages.deduce.nextgen;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;

import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.DG_ClassStatement;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.IdentIA;
import tripleo.elijah_elevateder.stages.instructions.ProcIA;

import java.util.*;

public abstract class DR_Ident implements DR_Item {
	public interface Understanding {
		String asString();
	}

	public static @NotNull DR_Ident create(final IdentExpression aIdent,
	                                       final VariableTableEntry aVteBl1,
	                                       final BaseEvaFunction aBaseEvaFunction) {
		return new DR_Ident(aIdent, aVteBl1, aBaseEvaFunction) {
			@Override
			public _S _s() {
				return new _S() {
					@Override
					public String getName() {
						return aIdent.getText();
					}

					@Override
					public boolean isResolved() {
						return aVteBl1.getStatus() == BaseTableEntry.Status.KNOWN;
					}

					@Override
					public @Nullable String simplified() {
						return null;
					}
				};
			}
		};
	}

	public static @NotNull DR_Ident create(@NotNull IdentTableEntry aIdentTableEntry, BaseEvaFunction aGeneratedFunction) {
		return new DR_Ident(aIdentTableEntry, aGeneratedFunction) {
			@Override
			public _S _s() {
				return new _S() {
					@Override
					public String getName() {
						return aIdentTableEntry.getIdent().getText();
					}

					@Override
					public boolean isResolved() {
						return aIdentTableEntry.isResolved();
					}

					@Override
					public @Nullable String simplified() {
						return null;
					}
				};
			}
		};
	}

	public static @NotNull DR_Ident create(final VariableTableEntry aVariableTableEntry, final BaseEvaFunction aGeneratedFunction) {
		return new DR_Ident(aVariableTableEntry, aGeneratedFunction) {
			@Override
			public _S _s() {
				return new _S() {
					@Override
					public String getName() {
						return aVariableTableEntry.getName();
					}

					@Override
					public boolean isResolved() {
						//assert mode == 2;
						return aVariableTableEntry.getStatus() == BaseTableEntry.Status.KNOWN;
					}

					@Override
					public @Nullable String simplified() {
						return null;
					}
				};
			}
		};
	}

	private final List<DT_ResolveObserver> resolveObserverList = new LinkedList<>();

	private final @Nullable IdentExpression ident;

	private final @Nullable VariableTableEntry vteBl1;

	@NotNull
	List<DoneCallback<DR_PossibleType>> typePossibles = new ArrayList<>();

	boolean _b;

	private final @Nullable IdentTableEntry _identTableEntry;

	private final BaseEvaFunction baseEvaFunction;

	private final int mode;

	private final DeferredObject<DR_PossibleType, Void, Void> typePossibleDeferred = new DeferredObject<>();

	private final List<DR_PossibleType> typeProposals = new ArrayList<>();

	public final List<Understanding> u = new ArrayList<>();

	public DR_Ident(final IdentExpression aIdent, final VariableTableEntry aVteBl1,
			final BaseEvaFunction aBaseEvaFunction) {
		ident = aIdent;
		vteBl1 = aVteBl1;
		this._identTableEntry = null;
		baseEvaFunction = aBaseEvaFunction;
		mode = 1;
	}

	public DR_Ident(final @NotNull IdentTableEntry aIdentTableEntry, final BaseEvaFunction aBaseEvaFunction) {
		ident = aIdentTableEntry.getIdent();
		vteBl1 = null;
		_identTableEntry = aIdentTableEntry;
		baseEvaFunction = aBaseEvaFunction;
		mode = 1;
	}

	public DR_Ident(final VariableTableEntry aVteBl1, final BaseEvaFunction aBaseEvaFunction) {
		vteBl1 = aVteBl1;
		baseEvaFunction = aBaseEvaFunction;
		mode = 2;
		_identTableEntry = null;
		ident = null;
	}

	private void addElementUnderstanding(OS_Element x) {
		addUnderstanding(new DR_IdentUnderstandings.ElementUnderstanding(x));
//		addUnderstanding(__inj().new_DR_Ident_ElementUnderstanding(x));
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("104 addElementUnderstanding %s %s".formatted(name(), x));
	}

	public void addPossibleType(final DR_PossibleType aPt) {
		for (DoneCallback<DR_PossibleType> typePossible : typePossibles) {
			typePossible.onDone(aPt);
		}
	}

	public void addResolveObserver(final DT_ResolveObserver aDTResolveObserver) {
		resolveObserverList.add(aDTResolveObserver);
	}

	public void addUnderstanding(final @NotNull Understanding aUnderstanding) {
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("*** 162 Understanding DR_Ident >> " + this.simplified() +
		// " " + aUnderstanding.asString());
		u.add(aUnderstanding);
	}

	public void foo() {
	}

	public BaseEvaFunction getNode() {
		return baseEvaFunction;
	}

	public @Nullable IdentTableEntry identTableEntry() {
		return _identTableEntry;
	}

	public boolean isResolved() {
		for (Understanding understanding : u) {
			if (understanding instanceof DR_IdentUnderstandings.PTEUnderstanding ptu) {
				var ci = ptu.pte.getClassInvocation();
				var fi = ptu.pte.getFunctionInvocation();

				if (DebugFlags.FORCE_IGNORE) {
					ci.resolvePromise();
					fi.generatePromise();
				}

				return true;
			}
		}

		return _s().isResolved();
	}

	public String name() {
		return _s().getName();
	}

	public void onPossibleType(final DoneCallback<DR_PossibleType> cb) {
		// this.typePossibleDeferred.then(cb);
		typePossibles.add(cb);
	}

	public void proposeType(final DR_PossibleType aPt) {
		// if (_b) throw new IllegalStateException("Error"); // FIXME testing only call
		// once

		typeProposals.add(aPt);

		_b = true;
	}

	public void resolve() {
		if (_identTableEntry == null) {
			assert vteBl1 != null;

			assert vteBl1.getStatus() == BaseTableEntry.Status.KNOWN;

			vteBl1.elementPromise((OS_Element x) -> {
				addUnderstanding(new DR_IdentUnderstandings.ElementUnderstanding(x));
//				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("-- [DR_Ident:104] addElementUnderstanding for vte " + x);
			}, null);
			return;
		}

		assert _identTableEntry != null;
		_identTableEntry.elementPromise(this::addElementUnderstanding, null);
		_identTableEntry.backlinkSet().done(ia -> {
			if (ia instanceof ProcIA procIA) {
				var mainLogic = procIA.getEntry();

				if (mainLogic.expression_num instanceof IdentIA mlIdentIA) {
					@NotNull
					final IdentTableEntry mlident = mlIdentIA.getEntry();

					final DR_Ident ident1 = baseEvaFunction.getIdent(mlident);

					final String name = ident1.name();
					assert name.equals(mlident.getIdent().getText());

					addUnderstanding(new ProcedureCallUnderstanding(mainLogic));
				} else
					addUnderstanding(new DR_IdentUnderstandings.BacklinkUnderstanding(ia));
			} else
				addUnderstanding(new DR_IdentUnderstandings.BacklinkUnderstanding(ia));
		});
	}

	public void resolve(final DG_ClassStatement aDcs) {
		addUnderstanding(new DR_IdentUnderstandings.ClassUnderstanding(aDcs));
	}

	public void resolve(final @NotNull IElementHolder aEh, final ProcTableEntry aPte) {
		addUnderstanding(new DR_IdentUnderstandings.ElementUnderstanding(aEh.getElement()));
		addUnderstanding(new DR_IdentUnderstandings.PTEUnderstanding(aPte));
	}

	private @Nullable String simplified() {
		var x = _s().simplified();
		if (ident != null)
			return "ident: %s %s".formatted(ident.getText(), baseEvaFunction);
		if (vteBl1 != null)
			return "vte: %s %s".formatted(vteBl1.getName(), baseEvaFunction);
		return null;
	}

	@Override
	public @NotNull String toString() {
		return "DR_Ident{" + "ident=" + ident + ", vteBl1=" + vteBl1 + ", baseEvaFunction=" + baseEvaFunction
				+ ", mode=" + mode + ", typeProposals=" + typeProposals + ", typePossibleDeferred="
				+ typePossibleDeferred + ", _b=" + _b + ", typePossibles=" + typePossibles + '}';
	}

	public void try_resolve_normal(final @NotNull Context aContext) {
		LookupResultList lrl1 = aContext.lookup(this._identTableEntry.getIdent().getText());
		@Nullable
		OS_Element best = lrl1.chooseBest(null);

		for (DT_ResolveObserver resolveObserver : resolveObserverList) {
			resolveObserver.onElement(best);
		}
	}

	interface _S {
		String getName();

		boolean isResolved();

		@Nullable String simplified();
	}

	public abstract _S _s();
}
