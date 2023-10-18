package tripleo.elijah.stages.deduce;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.types.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_fn.IdentTableEntry.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.*;

import java.util.*;

class Unnamed_ITE_Resolver1 implements ITE_Resolver {
	public class FoundParent implements BaseTableEntry.StatusListener {
		interface DT_Rule {
			String ruleName();
		}

		record UIR1_Env() {
		}

		class UIR1_Rule implements DT_Rule {
			private final OS_Type aTy;

			UIR1_Rule(final OS_Type aATy, final @NotNull VariableStatement vs) {
				@NotNull
				TypeName typ = vs.typeName();

				var aTypeName = vs.typeName();
				assert (!aTypeName.isNull());

				aTy = aATy;
			}

			public @NotNull Operation<GenType> product() {
				GenType ty2;
				assert aTy.getTypeName() != null;
				try {
					ty2 = dt2.resolve_type(aTy, aTy.getTypeName().getContext());
					return Operation.success(ty2);
				} catch (ResolveError aE) {
					return Operation.failure(aE);
				}
			}

			@Override
			public @NotNull String ruleName() {
				return "Unnamed_ITE_Resolver1::getTY2";
			}
		}

		private final BaseTableEntry bte;

		private final Context ctx;

		private final BaseEvaFunction generatedFunction;

		private final IdentTableEntry ite;

		@Contract(pure = true)
		public FoundParent(BaseTableEntry aBte, IdentTableEntry aIte, Context aCtx,
				BaseEvaFunction aGeneratedFunction) {
			bte = aBte;
			ite = aIte;
			ctx = aCtx;
			generatedFunction = aGeneratedFunction;
		}

		private @Nullable GenType getTY2(final @NotNull VariableStatementImpl aVariableStatement,
				@NotNull TypeName aTypeName, @NotNull OS_Type aTy) throws ResolveError {
			if (aTy.getType() != OS_Type.Type.USER) {
				assert false;
				@NotNull
				GenType genType = _inj().new_GenTypeImpl();
				genType.set(aTy);
				return genType;
			}

			if (!aTypeName.isNull()) {
				final UIR1_Rule r = new UIR1_Rule(aTy, aVariableStatement);
				assert r.product().mode() == Mode.SUCCESS;
				return r.product().success();
			}

			@Nullable
			GenType ty2 = null;
			if (bte instanceof VariableTableEntry vte) {
				final TypeTableEntry vte_tte = vte.getTypeTableEntry();
				ty2 = _inj().new_GenTypeImpl();
				ty2.copy(vte_tte.genType);

				// TODO why not just return this ^^? instead of copying

				/*
				 * final OS_Type attached = vte_tte.getAttached(); if (attached == null) {
				 * type_is_null_and_attached_is_null_vte(); // ty2 will probably be null here }
				 * else { ty2 = _inj().new_GenTypeImpl(); ty2.set(attached); }
				 */
			} else if (bte instanceof IdentTableEntry) {
				final TypeTableEntry tte = ((IdentTableEntry) bte).type;
				if (tte != null) {
					final OS_Type attached = tte.getAttached();

					if (attached == null) {
						type_is_null_and_attached_is_null_ite((IdentTableEntry) bte);
						// ty2 will be null here
					} else {
						ty2 = _inj().new_GenTypeImpl();
						ty2.set(attached);
					}
				}
			}

			return ty2;
		}

		@Override
		public void onChange(@NotNull IElementHolder eh, BaseTableEntry.Status newStatus) {
			if (newStatus == BaseTableEntry.Status.KNOWN) {
				if (bte instanceof final @NotNull VariableTableEntry vte) {
					onChangeVTE(vte);
				} else if (bte instanceof final @NotNull ProcTableEntry pte) {
					onChangePTE(pte);
				} else if (bte instanceof final @NotNull IdentTableEntry ite) {
					onChangeITE(ite);
				}
				postOnChange(eh);
			}
		}

		private void onChangeITE(@NotNull IdentTableEntry identTableEntry) {
			final DT_Env env = _inj().new_DT_Env(dt2.LOG, dt2._errSink(), dt2.central());
			final TypeTableEntry ite_type = identTableEntry.type;

			if (ite_type != null) {
				final OS_Type ty = ite_type.getAttached();

				@Nullable
				OS_Element ele2 = null;

				try {
					if (ty.getType() == OS_Type.Type.USER) {
						@NotNull
						GenType ty2 = dt2.resolve_type(ty, ty.getTypeName().getContext());
						OS_Element ele;
						if (ite_type.genType.getResolved() == null) {
							if (ty2.getResolved().getType() == OS_Type.Type.USER_CLASS) {
								ite_type.genType.copy(ty2);
							}
						}
						ele = ty2.getResolved().getElement();
						LookupResultList lrl = DeduceLookupUtils.lookupExpression(this.ite.getIdent(), ele.getContext(),
								dt2);
						ele2 = lrl.chooseBest(null);
					} else
						ele2 = ty.getClassOf(); // TODO might fail later (use getElement?)

					@Nullable
					LookupResultList lrl = null;

					lrl = DeduceLookupUtils.lookupExpression(this.ite.getIdent(), ele2.getContext(), dt2);
					@Nullable
					OS_Element best = lrl.chooseBest(null);
					// README commented out because only firing for dir.listFiles, and we always use
					// `best'
//					if (best != ele2) LOG.err(String.format("2824 Divergent for %s, %s and %s", identTableEntry, best, ele2));;
					this.ite.setStatus(BaseTableEntry.Status.KNOWN, dt2._inj().new_GenericElementHolder(best));
				} catch (ResolveError aResolveError) {
					aResolveError.printStackTrace();
					dt2._errSink().reportDiagnostic(aResolveError);
				}
			} else {
				if (!identTableEntry.fefi) {

					final Found_Element_For_ITE fefi = dt2._inj().new_Found_Element_For_ITE(generatedFunction, ctx, env,
							dt2._inj().new_DeduceClient1(dt2));
					fefi.action(identTableEntry);
					identTableEntry.fefi = true;
					identTableEntry.onFefiDone(new DoneCallback<GenType>() {
						@Override
						public void onDone(final @NotNull GenType result) {
							LookupResultList lrl = null;
							OS_Element ele2;
							try {
								lrl = DeduceLookupUtils.lookupExpression(ite.getIdent(),
										result.getResolved().getClassOf().getContext(), dt2);
								ele2 = lrl.chooseBest(null);

								if (ele2 != null) {
									ite.setStatus(BaseTableEntry.Status.KNOWN,
											dt2._inj().new_GenericElementHolder(ele2));
									ite.resolveTypeToClass(result.getNode());

									if (ite.getCallablePTE() != null) {
										final @Nullable ProcTableEntry pte = ite.getCallablePTE();
										final @NotNull IInvocation invocation = result.getCi();
										final @NotNull FunctionInvocation fi = dt2.newFunctionInvocation(
												(BaseFunctionDef) ele2, pte, invocation, dt2.phase);

										generatedFunction.addDependentFunction(fi);
									}
								}
							} catch (ResolveError aResolveError) {
								aResolveError.printStackTrace();
							}
						}
					});
				}
				// TODO we want to setStatus but have no USER or USER_CLASS to perform lookup
				// with
			}
		}

		private void onChangePTE(@NotNull ProcTableEntry aPte) {
			if (aPte.getStatus() == BaseTableEntry.Status.KNOWN) { // TODO might be obvious
				if (aPte.getFunctionInvocation() != null) {
					FunctionInvocation fi = aPte.getFunctionInvocation();
					FunctionDef fd = fi.getFunction();
					if (fd instanceof ConstructorDef) {
						fi.generateDeferred().then(new DoneCallback<BaseEvaFunction>() {
							@Override
							public void onDone(BaseEvaFunction result) {
								@NotNull IEvaConstructor constructorDef = (IEvaConstructor) result;
								@NotNull FunctionDef     ele            = constructorDef.getFD();

								try {
									LookupResultList lrl = DeduceLookupUtils.lookupExpression(ite.getIdent(),
											ele.getContext(), dt2);
									@Nullable
									OS_Element best = lrl.chooseBest(null);
									assert best != null;
									ite.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_GenericElementHolder(best));
								} catch (ResolveError aResolveError) {
									aResolveError.printStackTrace();
									dt2._errSink().reportDiagnostic(aResolveError);
								}
							}
						});
					}
				} else
					throw new NotImplementedException();
			} else {
				dt2.LOG.info("1621");
				@Nullable
				LookupResultList lrl = null;
				try {
					lrl = DeduceLookupUtils.lookupExpression(ite.getIdent(), ctx, dt2);
					@Nullable
					OS_Element best = lrl.chooseBest(null);
					assert best != null;
					ite.setResolvedElement(best);
					dt2.found_element_for_ite(null, ite, best, ctx, dt2.central());
//						ite.setStatus(BaseTableEntry.Status.KNOWN, best);
				} catch (ResolveError aResolveError) {
					aResolveError.printStackTrace();
				}
			}
		}

		private void onChangeVTE(@NotNull VariableTableEntry vte) {
			@NotNull
			List<TypeTableEntry> pot = dt2.getPotentialTypesVte(vte);
			if (vte.getStatus() == BaseTableEntry.Status.KNOWN && vte.getTypeTableEntry().getAttached() != null
					&& vte.getResolvedElement() != null) {

				final OS_Type ty = vte.getTypeTableEntry().getAttached();

				@Nullable
				OS_Element ele2 = null;

				try {
					if (ty.getType() == OS_Type.Type.USER) {
						@NotNull
						GenType ty2 = dt2.resolve_type(ty, ty.getTypeName().getContext());
						OS_Element ele;
						if (vte.getTypeTableEntry().genType.getResolved() == null) {
							if (ty2.getResolved().getType() == OS_Type.Type.USER_CLASS) {
								vte.getTypeTableEntry().genType.copy(ty2);
							}
						}
						ele = ty2.getResolved().getElement();
						LookupResultList lrl = DeduceLookupUtils.lookupExpression(ite.getIdent(), ele.getContext(), dt2);
						ele2 = lrl.chooseBest(null);
					} else
						ele2 = ty.getElement();

					if (ty instanceof OS_FuncType) {
						vte.typePromise().then(new DoneCallback<GenType>() {
							@Override
							public void onDone(final @NotNull GenType result) {
								OS_Element ele3 = result.getResolved().getClassOf();
								@Nullable
								LookupResultList lrl = null;

								try {
									lrl = DeduceLookupUtils.lookupExpression(ite.getIdent(), ele3.getContext(), dt2);

									@Nullable
									OS_Element best = lrl.chooseBest(null);
									// README commented out because only firing for dir.listFiles, and we always use
									// `best'
									// if (best != ele2) LOG.err(String.format("2824 Divergent for %s, %s and %s",
									// ite, best, ele2));;
									ite.setStatus(BaseTableEntry.Status.KNOWN,
											_inj().new_GenericElementHolderWithType(best, ty, dt2));
								} catch (ResolveError aResolveError) {
									aResolveError.printStackTrace();
									dt2._errSink().reportDiagnostic(aResolveError);
								}
							}
						});
					} else {
						@Nullable
						LookupResultList lrl = DeduceLookupUtils.lookupExpression(ite.getIdent(), ele2.getContext(),
								dt2);
						@Nullable
						OS_Element best = lrl.chooseBest(null);
						// README commented out because only firing for dir.listFiles, and we always use `best'
						// if (best != ele2) LOG.err(String.format("2824 Divergent for %s, %s and %s", ite, best, ele2));;
						ite.setStatus(BaseTableEntry.Status.KNOWN, _inj().new_GenericElementHolderWithType(best, ty, dt2));
					}
				} catch (ResolveError aResolveError) {
					aResolveError.printStackTrace();
					dt2._errSink().reportDiagnostic(aResolveError);
				}
			} else if (pot.size() == 1) {
				TypeTableEntry tte = pot.get(0);
				@Nullable
				OS_Type ty = tte.getAttached();
				if (ty != null) {
					switch (ty.getType()) {
					case USER:
						vte_pot_size_is_1_USER_TYPE(vte, ty);
						break;
					case USER_CLASS:
						vte_pot_size_is_1_USER_CLASS_TYPE(vte, ty);
						break;
					default:
						throw new IllegalStateException("Error");
					}
				}
			}
		}

		/* @ensures ite.type != null; */
		private void postOnChange(@NotNull IElementHolder eh) {
			if (ite.type == null && eh.getElement() instanceof final @NotNull VariableStatementImpl variableStatement) {
				@NotNull
				TypeName typ = variableStatement.typeName();
				@NotNull
				OS_Type ty = dt2._inj().new_OS_UserType(typ);

				try {
					@Nullable
					GenType ty2 = getTY2(variableStatement, typ, ty);

					// no expression or TableEntryIV below
					if (ty2 != null) {
						final @NotNull TypeTableEntry tte = generatedFunction
								.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, null);
						// trying to keep genType up to date

						if (!ty.getTypeName().isNull())
							tte.setAttached(ty);
						tte.setAttached(ty2);

						ite.type = tte;
						if (/* !ty.getTypeName().isNull() && */ !ty2.isNull()) {
							boolean skip = false;

							if (!ty.getTypeName().isNull()) {
								final TypeNameList gp = ((NormalTypeName) ty.getTypeName()).getGenericPart();
								if (gp != null) {
									if (gp.size() > 0 && ite.type.genType.getNonGenericTypeName() == null) {
										skip = true;
									}
								}
							}
							if (!skip)
								ite.type.genType.genCIForGenType2(dt2);
						}
					}
				} catch (ResolveError aResolveError) {
					dt2._errSink().reportDiagnostic(aResolveError);
				}
			}
		}

		private void type_is_null_and_attached_is_null_ite(final IdentTableEntry ite) {
			int y = 2;
			PromiseExpectation<GenType> pe = dt2.promiseExpectation(ite, "Null USER type attached resolved");
//			ite.onType(phase, _inj().new_OnType() {
//
//				@Override
//				public void typeDeduced(@NotNull OS_Type aType) {
//					// TODO Auto-generated method stub
//					pe.satisfy(aType);
//				}
//
//				@Override
//				public void noTypeFound() {
//					// TODO Auto-generated method stub
//
//				}
//			})
			// ;.done(new DoneCallback<GenType>() {
//				@Override
//				public void onDone(GenType result) {
//					pe.satisfy(result);
//					final OS_Type attached1 = result.resolved != null ? result.resolved : result.typeName;
//					if (attached1 != null) {
//						switch (attached1.getType()) {
//						case USER_CLASS:
//							FoundParent.this.ite.type = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, attached1);
//							break;
//						case USER:
//							try {
//								OS_Type ty3 = resolve_type(attached1, attached1.getTypeName().getContext());
//								// no expression or TableEntryIV below
//								@NotNull TypeTableEntry tte4 = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, null);
//								// README trying to keep genType up to date
//								tte4.setAttached(attached1);
//								tte4.setAttached(ty3);
//								FoundParent.this.ite.type = tte4; // or ty2?
//							} catch (ResolveError aResolveError) {
//								aResolveError.printStackTrace();
//							}
//							break;
//						}
//					}
//				}
//			});
		}

		private void type_is_null_and_attached_is_null_vte() {
			// LOG.err("2842 attached == null for "+((VariableTableEntry) bte).type);
			@NotNull
			PromiseExpectation<GenType> pe = dt2.promiseExpectation((VariableTableEntry) bte,
																	"Null USER type attached resolved");
			VTE_TypePromises.found_parent(pe, generatedFunction, ((VariableTableEntry) bte), ite, dt2);
		}

		interface DT_Rule {
			String ruleName();
		}

		static final class UIR1_Env {
			UIR1_Env() {
			}

			@Override
			public int hashCode() {
				return 1;
			}

			@Override
			public boolean equals(Object obj) {
				return obj == this || obj != null && obj.getClass() == this.getClass();
			}

			@Override
			public String toString() {
				return "UIR1_Env[]";
			}

		}

		class UIR1_Rule implements DT_Rule {
			private final OS_Type aTy;

			UIR1_Rule(final OS_Type aATy, final @NotNull VariableStatement vs) {
				@NotNull
				TypeName typ = vs.typeName();

				var aTypeName = vs.typeName();
				assert (!aTypeName.isNull());

				aTy = aATy;
			}

			public @NotNull Operation<GenType> product() {
				GenType ty2;
				assert aTy.getTypeName() != null;
				try {
					ty2 = dt2.resolve_type(aTy, aTy.getTypeName().getContext());
					return Operation.success(ty2);
				} catch (ResolveError aE) {
					return Operation.failure(aE);
				}
			}

			@Override
			public @NotNull String ruleName() {
				return "Unnamed_ITE_Resolver1::getTY2";
			}
		}
	}

}
