/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.deduce;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.OS_TypeNameElement;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.AbstractCodeGen;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_fluffy.util.*;

import java.util.function.Supplier;

/**
 * Created 11/18/21 12:02 PM
 */
public class DeduceTypeResolve {
	public class _StatusListener__BTE_203 implements BaseTableEntry.StatusListener {
		private void _203_backlink_is_PTE(final @NotNull GenType result, final ProcTableEntry procTableEntry,
				final @NotNull IElementHolder eh) {
			// README
			// 1. Resolve type of pte (above) to a class
			// 2. Convert bte to ite
			// 3. Get DeduceElement3
			// 4. Pass it off

			// README classStatement [T310-231]
			final ClassStatement classStatement = result.getResolved().getClassOf();

			if (!(bte instanceof final IdentTableEntry identTableEntry_bte)) {
				throw new AssertionError();
			} else {
				final DeduceElement3_IdentTableEntry de3_ite = identTableEntry_bte.getDeduceElement3();

				// Just testing
				final DeduceElement3_IdentTableEntry de3_ite2 = identTableEntry_bte._deduceTypes2()._zero_getIdent(
						identTableEntry_bte, identTableEntry_bte.__gf, identTableEntry_bte._deduceTypes2());
				assert de3_ite2.principal == de3_ite.principal;

				// Also testing, but not essential.
				// assert identTableEntry_bte.getCallablePTE() != null && procTableEntry ==
				// identTableEntry_bte.getCallablePTE();

				de3_ite.backlinkPte(classStatement, procTableEntry, eh);
			}
		}

		private void _203_backlink_is_VTE(final @NotNull GenType aGenType, final IElementHolder eh,
				final @NotNull VariableTableEntry variableTableEntry) {
			if (eh instanceof final Resolve_Ident_IA.@NotNull GenericElementHolderWithDC eh1) {
				final DeduceTypes2.DeduceClient3 dc = eh1.getDC();
				dc.genCIForGenType2(aGenType);
			}
			// maybe set something in ci to INHERITED, but that's what DeduceProcCall is for
			if (eh.getElement() instanceof FunctionDef) {
				if (aGenType.getNode() instanceof final @NotNull EvaClass evaClass) {
					evaClass.functionMapDeferred((FunctionDef) eh.getElement(), aGenType::setNode);
				}
			}
			variableTableEntry.getTypeTableEntry().setAttached(aGenType);
		}

		private void _203_backlink_isIDTE(final @NotNull GenType result,
				final @NotNull IdentTableEntry identTableEntry) {
			if (identTableEntry.type == null) {
				identTableEntry.type = _inj().new_TypeTableEntry(999, TypeTableEntry.Type.TRANSIENT,
						result.getTypeName(), identTableEntry.getIdent(), null); // FIXME 999
			}

			identTableEntry.type.setAttached(result);
		}

		@Override
		public void onChange(final @NotNull IElementHolder eh, final BaseTableEntry.Status newStatus) {
			if (newStatus != BaseTableEntry.Status.KNOWN)
				return;

			if (backlink instanceof final @NotNull IdentTableEntry identTableEntry) {
				identTableEntry.typeResolvePromise().done(result -> _203_backlink_isIDTE(result, identTableEntry));
			} else if (backlink instanceof final @NotNull VariableTableEntry variableTableEntry) {
				variableTableEntry.typeResolvePromise()
						.done(result -> _203_backlink_is_VTE(result, eh, variableTableEntry));
			} else if (backlink instanceof final ProcTableEntry procTableEntry) {
				procTableEntry.typeResolvePromise().done(result -> _203_backlink_is_PTE(result, procTableEntry, eh));
			}
		}
	}

	class _StatusListener__BTE_86 implements BaseTableEntry.StatusListener {
		@NotNull
		GenType genType = _inj().new_GenTypeImpl();

		public void logProgress(int ignoredCode, String message) {
			SimplePrintLoggerToRemoveSoon.println_err_2(message);
		}

		@Override
		public void onChange(final @NotNull IElementHolder eh, final BaseTableEntry.Status newStatus) {
			if (newStatus != BaseTableEntry.Status.KNOWN)
				return;

			eh.getElement().visitGen(new AbstractCodeGen() {
				@Override
				public void addClass(final @NotNull ClassStatement klass) {
					genType.setResolved(klass.getOS_Type());

					if (eh instanceof DeduceElement3_IdentTableEntry.DE3_ITE_Holder) {
						DeduceElement3_IdentTableEntry.DE3_ITE_Holder de3_ite_holder = (DeduceElement3_IdentTableEntry.DE3_ITE_Holder) eh;
						de3_ite_holder.genTypeAction(_inj().new_SGTA_SetResolvedClass(klass));
					}
				}

				@Override
				public void visitCaseScope(final CaseConditional.CaseScope aCaseScope) {
					throw new UnintendedUseException();
				}

				@Override
				public void defaultAction(final OS_Element anElement) {
					logProgress(158, "158 " + anElement);
					throw new IllegalStateException();
				}

				@Override
				public void visitAliasStatement(final @NotNull AliasStatement aAliasStatement) {
					logProgress(127, String.format("** AliasStatementImpl %s points to %s", aAliasStatement.name(),
							aAliasStatement.getExpression()));
				}

				@Override
				public void visitConstructorDef(final ConstructorDef aConstructorDef) {
					int y = 2;
				}

				@Override
				public void visitDefFunction(final @NotNull DefFunctionDef aDefFunctionDef) {
					logProgress(138, String.format("** DefFunctionDef %s is %s", aDefFunctionDef.name(),
							((StatementWrapper) aDefFunctionDef.getItems().iterator().next()).getExpr()));
				}

				@Override
				public void visitFormalArgListItem(final @NotNull FormalArgListItem aFormalArgListItem) {
					final OS_Type attached;
					if (bte instanceof VariableTableEntry)
						attached = ((VariableTableEntry) bte).getTypeTableEntry().getAttached();
					else if (bte instanceof IdentTableEntry) {
						final IdentTableEntry identTableEntry = (IdentTableEntry) DeduceTypeResolve.this.bte;
						if (identTableEntry.type == null)
							return;
						attached = identTableEntry.type.getAttached();
					} else
						throw new IllegalStateException("invalid entry (bte) " + bte);

					if (attached != null)
						logProgress(155,

								String.format("** FormalArgListItem %s attached is not null. Type is %s. Points to %s",
										aFormalArgListItem.name(), aFormalArgListItem.typeName(), attached));
					else
						logProgress(159, String.format("** FormalArgListItem %s attached is null. Type is %s.",
								aFormalArgListItem.name(), aFormalArgListItem.typeName()));
				}

				@Override
				public void visitFunctionDef(final @NotNull FunctionDef aFunctionDef) {
					genType.setResolved(aFunctionDef.getOS_Type());
				}

				@Override
				public void visitIdentExpression(final IdentExpression aIdentExpression) {
					_inj().new_DTR_IdentExpression(DeduceTypeResolve.this, aIdentExpression, bte).run(eh, genType);
				}

				@Override
				public void visitMC1(final MC1 aMC1) {
					if (aMC1 instanceof final @NotNull MatchArm_TypeMatch typeMatch) {
						NotImplementedException.raise();
					}
				}

				@Override
				public void visitPropertyStatement(final @NotNull PropertyStatement aPropertyStatement) {
					genType.setTypeName(_inj().new_OS_UserType(aPropertyStatement.getTypeName()));
					// TODO resolve??
				}

				@Override
				public void visitTypeNameElement(final OS_TypeNameElement aOS_typeNameElement) {

				}

				@Override
				public void visitVariableStatement(final VariableStatement variableStatement) {
					final DTR_VariableStatement dtr_v = _inj().new_DTR_VariableStatement(DeduceTypeResolve.this,
							variableStatement);

					if (bte instanceof IdentTableEntry ite1) {
						ite1._cheat_variableStatement = variableStatement;
					}

					dtr_v.run(_dt2s.get(), eh, genType);
				}
			});

			if (!typeResolution.isPending()) {
				int y = 2;
			} else {
				if (!genType.isNull())
					typeResolution.resolve(genType);
			}
		}
	}

	private final BaseTableEntry bte;
	@Nullable
	BaseTableEntry backlink;

	private final DeferredObject2<GenType, ResolveError, Void> typeResolution = new DeferredObject2<>();

	// private final DeduceTypes2 _dt2;
	private final Supplier<DeduceTypes2> _dt2s;

	public DeduceTypeResolve(BaseTableEntry aBte, final Supplier<DeduceTypes2> aDt2) {
		bte = aBte;
		_dt2s = aDt2;

		if (bte instanceof IdentTableEntry) {
			((IdentTableEntry) bte).backlinkSet().then(new DoneCallback<InstructionArgument>() {
				@Override
				public void onDone(final InstructionArgument backlink0) {
					if (backlink0 instanceof IdentIA) {
						backlink = ((IdentIA) backlink0).getEntry();
						setBacklinkCallback();
					} else if (backlink0 instanceof IntegerIA) {
						backlink = ((IntegerIA) backlink0).getEntry();
						setBacklinkCallback();
					} else if (backlink0 instanceof ProcIA) {
						backlink = ((ProcIA) backlink0).getEntry();
						setBacklinkCallback();
					} else
						backlink = null;
				}
			});
		} else if (bte instanceof VariableTableEntry) {
			backlink = null;
		} else if (bte instanceof ProcTableEntry) {
			backlink = null;
		} else
			throw new IllegalStateException();

		if (backlink != null) {
		} else {
			typeResolution().then(gt -> {
				if (!bte.typeResolvePromise().isResolved()) { // TODO 07/20 everywhere where this check is is wrong, BTW
					bte.typeResolve(gt);
				}
			});
			_inj_then(inj -> {
				bte.addStatusListener(inj.new__StatusListener__BTE_86(this));
			});
		}

		if (bte instanceof ProcTableEntry bte_pte) {
			typeResolution().then(x -> {
				if (!typeResolution().isResolved())
					bte_pte.typeDeferred().resolve(x);
			});
			typeResolution().fail(x -> bte_pte.typeDeferred().reject(x));
		}
	}

	private DeduceTypes2.DeduceTypes2Injector _inj() {
		return _dt2s.get()._inj();
	}

	private void _inj_then(final DoneCallback<DeduceTypes2.DeduceTypes2Injector> i) {
		if (_dt2s instanceof PromiseReadySupplier<DeduceTypes2> prs) {
			prs.then(q -> i.onDone(q._inj()));
		}
	}

	public @NotNull DeferredObject2<GenType, ResolveError, Void> getDeferred() {
		return typeResolution;
	}

	protected void setBacklinkCallback() {
		_inj_then(inj -> {
			assert backlink != null;
			backlink.addStatusListener(inj.new__StatusListener__BTE_203(this));
		});
	}

	public Promise<GenType, ResolveError, Void> typeResolution() {
		return typeResolution.promise();
	}

	public void typeResolve(GenType type1) {
		typeResolution.resolve(type1);
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
