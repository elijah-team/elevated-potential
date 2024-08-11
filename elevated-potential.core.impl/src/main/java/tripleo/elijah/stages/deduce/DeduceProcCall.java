/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.deduce.tastic.*;
import tripleo.elijah.stages.gen_fn.*;

/**
 * Created 11/30/21 11:56 PM
 */
public class DeduceProcCall {
	public class DeclTarget implements DeduceElement {
		private @NotNull final DeclAnchor anchor;
		private @NotNull final OS_Element element;

		/**
		 * $element(FunctionDef Directory.listFiles) is a $anchorType(MEMBER) of
		 * anchorElement(ClassSt std.io::Directory) and invocation just happens to be
		 * around (invocation.pte is the call site (MainLogic::main))
		 */
		public DeclTarget(final @NotNull OS_Element aBest,
				final @NotNull OS_Element aDeclAnchor,
				final @NotNull DeclAnchor.AnchorType aAnchorType) throws FCA_Stop {
			element = aBest;
			anchor = _g_deduceTypes2._inj().new_DeclAnchor(aDeclAnchor, aAnchorType);
			final Eventual<IInvocation> invocationP = new Eventual<>();
			invocationP.then(anchor::setInvocation);
			invocationP.onFail(new FailCallback<Diagnostic>() {
				@Override
				public void onFail(final Diagnostic result) {
					throw new UnintendedUseException("bork for now");
				}
			});

			switch (aAnchorType) {
			case VAR -> {
				DR_Variable v = _g_generatedFunction.getVar((VariableStatement) element);
				if (v.declaredTypeIsEmpty()) {
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("8787 declaredTypeIsEmpty for " + ((VariableStatement) element).getName());
					throw new FCA_Stop();
				} else {
					final NormalTypeName normalTypeName = (NormalTypeName) ((VariableStatementImpl) element).typeName();
					final LookupResultList lrl = normalTypeName.getContext().lookup(normalTypeName.getName());
					final ClassStatement classStatement = (ClassStatement) lrl.chooseBest(null);
					ClassInvocationMake
							.withGenericPart2(classStatement, null, normalTypeName, _g_deduceTypes2)
							.into(invocationP);
				}
			}
			default -> {
				if (element instanceof FunctionDef fd) {
					invocationP.resolve(_g_generatedFunction.fi);
				} else {
					IInvocation declaredInvocation = _g_generatedFunction.fi.getClassInvocation();
					if (declaredInvocation == null) {
						declaredInvocation = _g_generatedFunction.fi.getNamespaceInvocation();
					}

					if (aAnchorType == DeclAnchor.AnchorType.INHERITED) {
						assert declaredInvocation instanceof ClassInvocation;
						_g_deduceTypes2._inj().new_DerivedClassInvocation2((ClassStatement) aDeclAnchor,
																		   (ClassInvocation) declaredInvocation, new ReadySupplier_1<>(_g_deduceTypes2))
								.into(invocationP);
					} else {
						invocationP.resolve(declaredInvocation);
					}
				}
			}
			}
		}

		@Contract(pure = true)
		@Override
		public @NotNull DeclAnchor declAnchor() {
			return anchor;
		}

		@Contract(pure = true)
		@Override
		public @NotNull OS_Element element() {
			return element;
		}
	}

	private final @NotNull ProcTableEntry procTableEntry;
	private final DT_Resolvable11<DeduceElement> _pr_target = new DT_Resolvable11<>();
	private final DeferredObject2<DeduceElement, FCA_Stop, Void> _p_targetP2 = new DeferredObject2<>();
	private Context _g_context;
	private DeduceTypes2    _g_deduceTypes2;
	private ErrSink         _g_errSink;
	private BaseEvaFunction _g_generatedFunction;

	private DeduceElement target;

	@Contract(pure = true)
	public DeduceProcCall(final @NotNull ProcTableEntry aProcTableEntry) {
		procTableEntry = aProcTableEntry;

		procTableEntry.onFunctionInvocation((final @NotNull FunctionInvocation functionInvocation) -> {
			functionInvocation.generatePromise().then((BaseEvaFunction evaFunction) -> {
				final @NotNull FunctionDef best = evaFunction.getFD();

				final DeclAnchor.AnchorType anchorType = DeclAnchor.AnchorType.MEMBER;
				final OS_Element declAnchor = best.getParent();
				try {
					setTarget(aProcTableEntry._inj().new_DeclTarget(best, declAnchor, anchorType, this));
					_pr_target.resolve(getTarget());
					_p_targetP2.resolve(getTarget());
				} catch (FCA_Stop aE) {
					_p_targetP2.reject(aE);
				}
			});
		});
	}

	public BaseEvaFunction _generatedFunction() {
		return _g_generatedFunction;
	}

	public DeduceElement getTarget() {
		return target;
	}

	public void setDeduceTypes2(final DeduceTypes2 aDeduceTypes2, final Context aContext,
			final BaseEvaFunction aGeneratedFunction, final ErrSink aErrSink) {
		_g_deduceTypes2 = aDeduceTypes2;
		_g_context = aContext;
		_g_generatedFunction = aGeneratedFunction;
		_g_errSink = aErrSink;
	}

	public void setTarget(DeduceElement aTarget) {
		target = aTarget;
	}

	public @Nullable DT_Resolvable11<DeduceElement> targetP() {
		return _pr_target;
	}

	public @Nullable Promise<DeduceElement, FCA_Stop, Void> targetP2() {
		return _p_targetP2;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
