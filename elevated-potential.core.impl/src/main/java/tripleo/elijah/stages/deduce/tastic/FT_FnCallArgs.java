/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce.tastic;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;

import java.util.*;

import static tripleo.elijah.stages.deduce.DeduceTypes2.*;

public class FT_FnCallArgs implements ITastic {
	public ElLog LOG() {
		return LOG;
	}

	public DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	public static class NullFoundElement extends FoundElement {
		public NullFoundElement(DeduceTypes2.@NotNull DeduceClient4 dc) {
			super(dc.getPhase());
		}

		@Override public void foundElement(final OS_Element e) {}

		@Override public void noFoundElement() {}
	}

	/**
	 * Created 12/12/21 12:30 AM
	 */
		public static final class DoAssignCall {
		private final          DeduceTypes2.DeduceClient4 dc;
		private final @NotNull BaseEvaFunction            generatedFunction;

		/**
		 *
		 */
		public DoAssignCall(DeduceTypes2.DeduceClient4 dc,
							@NotNull BaseEvaFunction generatedFunction) {
			this.dc                = dc;
			this.generatedFunction = generatedFunction;
		}

		public OS_Module getModule() {
			return dc.getModule();
		}

		public @NotNull ElLog getLOG() {
			return dc.getLOG();
		}

		public ErrSink getErrSink() {
			return dc.getErrSink();
		}

		public DeduceTypes2.DeduceClient4 dc() {
			return dc;
		}

		public @NotNull BaseEvaFunction generatedFunction() {
			return generatedFunction;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (DoAssignCall) obj;
			return Objects.equals(this.dc, that.dc) &&
					Objects.equals(this.generatedFunction, that.generatedFunction);
		}

		@Override
		public int hashCode() {
			return Objects.hash(dc, generatedFunction);
		}

		@Override
		public String toString() {
			return "DoAssignCall[" +
					"dc=" + dc + ", " +
					"generatedFunction=" + generatedFunction + ']';
		}

		}

	private final @NotNull ElLog        LOG;
	private final @NotNull DeduceTypes2 deduceTypes2;
	private final          FnCallArgs   fca;

	@Contract(pure = true)
	public FT_FnCallArgs(final @NotNull DeduceTypes2 aDeduceTypes2, final FnCallArgs aFnCallArgs) {
		deduceTypes2 = aDeduceTypes2;
		fca          = aFnCallArgs;
		//
		LOG          = aDeduceTypes2.LOG;
	}

	public static final class Packet_do_assign_call implements Packet {
		private final @NotNull BaseEvaFunction generatedFunction;
		private final @NotNull Context         ctx;
		private final @NotNull IdentTableEntry idte;
		private final          int             instructionIndex;

		public Packet_do_assign_call(@NotNull BaseEvaFunction generatedFunction,
									 @NotNull Context ctx,
									 @NotNull IdentTableEntry idte,
									 int instructionIndex) {
			this.generatedFunction = generatedFunction;
			this.ctx               = ctx;
			this.idte              = idte;
			this.instructionIndex  = instructionIndex;
		}

		public @NotNull BaseEvaFunction generatedFunction() {
			return generatedFunction;
		}

		public @NotNull Context ctx() {
			return ctx;
		}

		public @NotNull IdentTableEntry idte() {
			return idte;
		}

		public int instructionIndex() {
			return instructionIndex;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (Packet_do_assign_call) obj;
			return Objects.equals(this.generatedFunction, that.generatedFunction) &&
					Objects.equals(this.ctx, that.ctx) &&
					Objects.equals(this.idte, that.idte) &&
					this.instructionIndex == that.instructionIndex;
		}

		@Override
		public int hashCode() {
			return Objects.hash(generatedFunction, ctx, idte, instructionIndex);
		}

		@Override
		public String toString() {
			return "Packet_do_assign_call[" +
					"generatedFunction=" + generatedFunction + ", " +
					"ctx=" + ctx + ", " +
					"idte=" + idte + ", " +
					"instructionIndex=" + instructionIndex + ']';
		}
	}

	@Override
	public void do_assign_call(final @NotNull BaseEvaFunction generatedFunction,
							   final @NotNull Context ctx,
							   final @NotNull IdentTableEntry idte,
							   final @NotNull FnCallArgs fca,
							   final @NotNull Instruction instruction) {
		final int                     instructionIndex = instruction.getIndex();
		final @NotNull ProcTableEntry pte = generatedFunction.getProcTableEntry(to_int(fca.getArg(0)));
		for (final @NotNull TypeTableEntry tte : pte.getArgs()) {
			LOG.info("771 " + tte);
			final IExpression e = tte.__debug_expression;
			if (e == null)
				continue;
			switch (e.getKind()) {
			case NUMERIC -> {
				tte.setAttached(deduceTypes2._inj().new_OS_BuiltinType(BuiltInTypes.SystemInteger));
				idte.type = tte; // TODO why not addPotentialType ? see below for example
			}
			case IDENT -> {
				final @Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(((IdentExpression) e).getText());
				final @NotNull List<TypeTableEntry> ll = deduceTypes2.getPotentialTypesVte((EvaFunction) generatedFunction, vte_ia);

				if (ll.size() == 1) {
					tte.setAttached(ll.get(0).getAttached());
					idte.addPotentialType(instructionIndex, ll.get(0));
				} else {
					throw new NotImplementedException();
				}
			}
			default -> {
				throw new NotImplementedException();
			}
			}
		}

		final DLU2.LookupChain lc = new DLU2.LookupChain() {
			@Override
			public DLU2.LookupChain getParent() {
				return null;
			}

			@Override
			public DLU2.LookupNode getCurrent() {
				return DLU2.lookupNodeSimple("FT_FnCallArgs::do_assign_call");
			}
		};

		new DLU2().lookup2(lc,
						   new PteIdentLookupable(pte, ctx),
						   null,
						   new Packet_do_assign_call(generatedFunction, ctx, idte, instructionIndex),
						   this::do_assign_call__callback);
	}

	private void do_assign_call__callback(@Nullable OS_Element best, Packet ap) {
		final Packet_do_assign_call   p                 = (Packet_do_assign_call) ap;
		final BaseEvaFunction         generatedFunction = p.generatedFunction();
		final @NotNull ProcTableEntry pte               = generatedFunction.getProcTableEntry(to_int(fca.getArg(0)));

		do_assign_call__callback(generatedFunction, best, pte);
	}

	private void do_assign_call__callback(final @NotNull BaseEvaFunction generatedFunction, final @Nullable OS_Element best, final @NotNull ProcTableEntry pte) {
		if (best != null) {
			pte.setResolvedElement(best);

			// TODO do we need to add a dependency for class, etc?
			if (true) {
				if (best instanceof ConstructorDef cd) {
					// TODO Dont know how to handle this
					int y = 2;
				} else if (best instanceof FunctionDef || best instanceof DefFunctionDef) {
					final OS_Element parent = best.getParent();
					IInvocation invocation;
					if (parent instanceof final @NotNull NamespaceStatement nsp) {
						invocation = deduceTypes2._inj().new_NamespaceInvocation(nsp);
					} else if (parent instanceof final @NotNull ClassStatement csp) {
						invocation = deduceTypes2._inj().new_ClassInvocation(csp, null, new ReadySupplier_1<>(deduceTypes2));
					} else {
						throw new NotImplementedException();
					}

					final FunctionInvocation fi = deduceTypes2.newFunctionInvocation((FunctionDef) best, pte, invocation, deduceTypes2.phase);
					generatedFunction.addDependentFunction(fi);
				} else if (best instanceof ClassStatement) {
					final GenType genType = GenTypeImpl.genCIFrom((ClassStatement) best, deduceTypes2);
					generatedFunction.addDependentType(genType);
				}
			}
		} else {
			throw new NotImplementedException();
		}
	}

	@Override
	public void do_assign_call(final @NotNull BaseEvaFunction generatedFunction,
	                           final @NotNull Context ctx,
	                           final @NotNull VariableTableEntry vte,
	                           final @NotNull Instruction instruction,
	                           final OS_Element aName) {
		final DeduceTypes2.DeduceClient4 client4          = deduceTypes2._inj().new_DeduceClient4(deduceTypes2);
		final DoAssignCall               dac              = deduceTypes2._inj().new_DoAssignCall(client4, generatedFunction, this);
		final int                        instructionIndex = instruction.getIndex();
		final @NotNull ProcTableEntry    pte              = ((ProcIA) fca.getArg(0)).getEntry();

		if (aName instanceof IdentExpression ie) {
			ie.getName().addUnderstanding(deduceTypes2._inj().new_ENU_FunctionCallTarget(pte));
			ie.getName().addUnderstanding(deduceTypes2._inj().new_ENU_TypeTransitiveOver(pte));
		}

		if (pte.typeDeferred().isResolved()) {
			pte.typeDeferred().then(gt -> {
				final TypeName nonGenericTypeName = gt.getNonGenericTypeName();

				if (null == nonGenericTypeName)
					return;

				var drType = generatedFunction.buildDrTypeFromNonGenericTypeName(nonGenericTypeName);

				gt.setDrType(drType);

				if (gt instanceof ForwardingGenType fgt) {
					fgt.unsparkled();
				}
			});
		}

		final ExpressionConfession ece = pte.expressionConfession();

		switch (ece.getType()) {
		case exp_num -> {
		}
		case exp -> {
		}
		default -> throw new IllegalStateException("Unexpected value: " + ece.getType());
		}

		var exp = pte.expression;
		var en = exp.getEntry();

		if (pte.expression_num instanceof @NotNull final IdentIA identIA) {

			// 08/13 out
			// 10/14 in
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("--------------------- 158" + (generatedFunction._getIdentIAResolvable(identIA).getNormalPath(generatedFunction, identIA)));

			final FT_FCA_IdentIA             fca_ident = deduceTypes2._inj().new_FT_FCA_IdentIA(FT_FnCallArgs.this, identIA, vte);
			final FT_FCA_IdentIA.Resolve_VTE rvte      = deduceTypes2._inj().new_FT_FCA_IdentIA_Resolve_VTE(vte, ctx, pte, instruction, fca);

			try {
				fca_ident.resolve_vte(dac, rvte);
				fca_ident.make2(dac, rvte);
				fca_ident.loop1(dac, rvte);
				fca_ident.loop2(dac, rvte);
			} catch (FCA_Stop ignored) {
			}
		} else if (pte.expression_num instanceof final @NotNull IntegerIA integerIA) {
			int y = 2;
			throw new UnintendedUseException();
		} else {
			throw new UnintendedUseException();
		}
	}

	private static class PteIdentLookupable implements Lookupable {
		private final @NotNull ProcTableEntry pte;
		private final @NotNull Context ctx;

		public PteIdentLookupable(final @NotNull ProcTableEntry aPte, final @NotNull Context aCtx) {
			pte = aPte;
			ctx = aCtx;
		}

		@Override
		public String s() {
			return ident().getText();
		}

		private IdentExpression ident() {
			return (IdentExpression) pte.__debug_expression;
		}

		@Override
		public Context ctx() {
			return ctx;
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
