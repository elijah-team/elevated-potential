package tripleo.elijah.stages.deduce;

import java.util.*;
import java.util.function.*;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.declarations.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.*;

class do_assign_normal_ident_deferred__DT_ResolveObserver implements DT_ResolveObserver {
	private final DeduceTypes2 deduceTypes2;
	private final @NotNull IdentTableEntry identTableEntry;
	private final @NotNull BaseEvaFunction generatedFunction;

	public do_assign_normal_ident_deferred__DT_ResolveObserver(final DeduceTypes2 aDeduceTypes2,
			final @NotNull IdentTableEntry aIdentTableEntry,
			final @NotNull BaseEvaFunction aGeneratedFunction) {
		deduceTypes2 = aDeduceTypes2;
		identTableEntry = aIdentTableEntry;
		generatedFunction = aGeneratedFunction;
	}

	@Override
	public void onElement(@Nullable OS_Element best) {
		if (best != null) {
			identTableEntry.setStatus(BaseTableEntry.Status.KNOWN, deduceTypes2._inj().new_GenericElementHolder(best));
			// TODO check for elements which may contain type information
			if (best instanceof final @NotNull VariableStatementImpl vs) {
				do_assign_normal_ident_deferred_VariableStatement(generatedFunction, identTableEntry, vs);
			} else if (best instanceof final @NotNull FormalArgListItem fali) {
				do_assign_normal_ident_deferred_FALI(generatedFunction, identTableEntry, fali);
			} else
				throw new NotImplementedException();
		} else {
			identTableEntry.setStatus(BaseTableEntry.Status.UNKNOWN, null);
			deduceTypes2.LOG.err("242 Bad lookup" + identTableEntry.getIdent().getText());
		}
	}

	public void do_assign_normal_ident_deferred_VariableStatement(final @NotNull BaseEvaFunction generatedFunction,
			final @NotNull IdentTableEntry aIdentTableEntry, final @NotNull VariableStatementImpl vs) {
		final DeduceElementWrapper parent = new DeduceElementWrapper(vs.getParent().getParent());
		final DeferredMember dm = deduceTypes2.deferred_member(parent, new X_S(generatedFunction).get(), vs,
				aIdentTableEntry);

		dm.typePromise().done(result -> {
			assert result.getResolved() != null;
			aIdentTableEntry.type.setAttached(result.getResolved());
		});

		final GenType genType = deduceTypes2._inj().new_GenTypeImpl();
		genType.setCi(dm.getInvocation());

		if (genType.getCi() instanceof NamespaceInvocation) {
			genType.setResolvedn(((NamespaceInvocation) genType.getCi()).getNamespace());
		} else if (genType.getCi() instanceof ClassInvocation) {
			genType.setResolved(((ClassInvocation) genType.getCi()).getKlass().getOS_Type());
		} else {
			throw new IllegalStateException();
		}
		generatedFunction.addDependentType(genType);
	}

	private void do_assign_normal_ident_deferred_FALI(final @NotNull BaseEvaFunction generatedFunction,
			final @NotNull IdentTableEntry aIdentTableEntry, final @NotNull FormalArgListItem fali) {
		final GenType genType = deduceTypes2._inj().new_GenTypeImpl();
		final FunctionInvocation functionInvocation = generatedFunction.fi;
		final String fali_name = fali.name();

		IInvocation invocation = null;
		if (functionInvocation.getClassInvocation() != null) {
			invocation = functionInvocation.getClassInvocation();
			genType.setResolved(((ClassInvocation) invocation).getKlass().getOS_Type());
		} else if (functionInvocation.getNamespaceInvocation() != null) {
			invocation = functionInvocation.getNamespaceInvocation();
			genType.setResolvedn(((NamespaceInvocation) invocation).getNamespace());
		}
		assert invocation != null;
		genType.setCi(Objects.requireNonNull(invocation));

		final @Nullable InstructionArgument vte_ia = generatedFunction.vte_lookup(fali_name);
		assert vte_ia != null;
		((IntegerIA) vte_ia).getEntry().typeResolvePromise().then(new DoneCallback<GenType>() {
			@Override
			public void onDone(final @NotNull GenType result) {
				assert result.getResolved() != null;
				aIdentTableEntry.type.setAttached(result);
			}
		});
		generatedFunction.addDependentType(genType);
		DebugPrint.addDependentType(generatedFunction, genType);
	}

	class X_S implements Supplier<IInvocation> {
		private final BaseEvaFunction generatedFunction1;

		public X_S(final BaseEvaFunction aGeneratedFunction) {
			generatedFunction1 = aGeneratedFunction;
		}

		@Override
		@NotNull
		public IInvocation get() {
			final IInvocation invocation;

			if (generatedFunction1.fi.getClassInvocation() != null) {
				invocation = generatedFunction1.fi.getClassInvocation();
			} else {
				invocation = generatedFunction1.fi.getNamespaceInvocation();
			}

			return invocation;
		}
	}
}
