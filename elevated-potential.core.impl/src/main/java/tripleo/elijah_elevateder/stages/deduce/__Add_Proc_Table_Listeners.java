package tripleo.elijah_elevateder.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

public class __Add_Proc_Table_Listeners {
	class SL implements BaseTableEntry.StatusListener {
		private final BaseEvaFunction generatedFunction;
		private final IdentTableEntry idte;
		private final ProcTableEntry  pte;
		private final IdentIA         identIA;
		private final DeduceTypes2    deduceTypes2;

		SL(final BaseEvaFunction aGeneratedFunction, final IdentTableEntry aIdte, final ProcTableEntry aPte,
				final IdentIA aIdentIA, final DeduceTypes2 aADeduceTypes2) {
			generatedFunction = aGeneratedFunction;
			idte = aIdte;
			pte = aPte;
			identIA = aIdentIA;
			deduceTypes2 = aADeduceTypes2;
		}

		@Override
		public void onChange(@NotNull IElementHolder eh, BaseTableEntry.Status newStatus) {
			if (newStatus != BaseTableEntry.Status.KNOWN)
				return;

			final OS_Element el = eh.getElement();

			@NotNull
			ElObjectType type = DecideElObjectType.getElObjectType(el);

			switch (type) {
			case NAMESPACE -> {
				@NotNull
				GenType genType = deduceTypes2._inj().new_GenTypeImpl((NamespaceStatement) el);
				generatedFunction.addDependentType(genType);
			}
			case CLASS -> {
				if (idte.type != null && idte.type.genType != null) {
					assert idte.type.genType.getResolved() != null;
					generatedFunction.addDependentType(idte.type.genType);
				} else {
					@NotNull
					GenType genType2 = deduceTypes2._inj().new_GenTypeImpl((ClassStatement) el);
					generatedFunction.addDependentType(genType2);
				}
			}
			case FUNCTION -> {
				@Nullable
				IdentIA identIA2 = null;
				if (pte.expression_num instanceof IdentIA)
					identIA2 = (IdentIA) pte.expression_num;
				if (identIA2 != null) {
					@NotNull
					IdentTableEntry idte2 = identIA.getEntry();
					@Nullable
					ProcTableEntry procTableEntry = idte2.getCallablePTE();
					if (procTableEntry == pte)
						SimplePrintLoggerToRemoveSoon.println_err_2("940 procTableEntry == pte");
					if (procTableEntry != null) {
						// TODO doesn't seem like we need this
						procTableEntry.onFunctionInvocation(functionInvocation -> {
							ClassInvocation ci = functionInvocation.getClassInvocation();
							NamespaceInvocation nsi = functionInvocation.getNamespaceInvocation();
							// do we register?? probably not
							assert ci != null || nsi != null;
							@NotNull
							FunctionInvocation fi = deduceTypes2.newFunctionInvocation((FunctionDef) el, pte,
									ci != null ? ci : nsi, deduceTypes2.phase);

							{
								if (functionInvocation.getClassInvocation() == fi.getClassInvocation()
										&& functionInvocation.getFunction() == fi.getFunction()
										&& functionInvocation.pte == fi.pte)
									SimplePrintLoggerToRemoveSoon
											.println_err_2("955 It seems like we are generating the same thing...");
								else {
									int ok = 2;
								}

							}
							generatedFunction.addDependentFunction(fi);
						});
						// END
					}
				}
			}
			case CONSTRUCTOR -> {
				int y = 2;
			}
			default -> deduceTypes2.LOG.err(String.format("228 Don't know what to do %s %s", type, el));
			}
		}
	}

	void add_proc_table_listeners(final @NotNull BaseEvaFunction generatedFunction, final @NotNull ProcTableEntry pte,
			final DeduceTypes2 aDeduceTypes2) {
		pte.addStatusListener(aDeduceTypes2._inj().new_ProcTableListener(pte, generatedFunction,
				aDeduceTypes2._inj().new_DeduceClient2(aDeduceTypes2)));

		InstructionArgument en = pte.expression_num;
		if (en != null) {
			if (en instanceof final @NotNull IdentIA identIA) {
				@NotNull IdentTableEntry idte = identIA.getEntry();
				idte.addStatusListener(new SL(generatedFunction, idte, pte, identIA, aDeduceTypes2));
			} else if (en instanceof IntegerIA) {
				// TODO this code does nothing so commented out
				/*
				 * final @NotNull IntegerIA integerIA = (IntegerIA) en;
				 * 
				 * @NotNull VariableTableEntry vte = integerIA.getEntry();
				 * vte.addStatusListener(_inj().new_BaseTableEntry.StatusListener() {
				 * 
				 * @Override public void onChange(IElementHolder eh, BaseTableEntry.Status
				 * newStatus) { if (newStatus != BaseTableEntry.Status.KNOWN) return;
				 * 
				 * @NotNull VariableTableEntry vte2 = vte;
				 * 
				 * final OS_Element el = eh.getElement();
				 * 
				 * @NotNull ElObjectType type = DecideElObjectType.getElObjectType(el);
				 * 
				 * switch (type) { case VAR: break; default: throw new
				 * NotImplementedException(); } } });
				 */
			} else
				throw new NotImplementedException();
		}
	}
}
