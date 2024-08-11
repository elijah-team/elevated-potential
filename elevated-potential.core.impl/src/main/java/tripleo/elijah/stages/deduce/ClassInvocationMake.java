package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.util.*;
import tripleo.elijah_fluffy.util.*;

public enum ClassInvocationMake {
	;

	public static @NotNull Operation<ClassInvocation> withGenericPart(@NotNull ClassStatement best,
																	  String constructorName,
																	  @Nullable NormalTypeName aTyn1,
			@NotNull DeduceTypes2 dt2) {
		Eventual<Operation<ClassInvocation>> ev = dt2.eventual("ClassInvocationMake::withGenericPart");

		if (aTyn1 == null) {
			// throw new IllegalStateException("blank typename");
		}

		final @NotNull GenericPart genericPart = dt2._inj().new_GenericPart(best, aTyn1);
		final @Nullable ClassInvocation clsinv      = dt2._inj().new_ClassInvocation(best, constructorName, new ReadySupplier_1<>(dt2));

		if (genericPart.hasGenericPart()) {
			final @NotNull List<TypeName> gp = best.getGenericPart();

			genericPart.normalTypeName().then(new DoneCallback<NormalTypeName>() {
				@Override
				public void onDone(final NormalTypeName result) {
					final TypeNameList gp2 = result.getGenericPart();

					if (gp2 != null) {
						for (int i = 0; i < gp.size(); i++) {
							final TypeName typeName = gp2.get(i);
							@NotNull
							GenType typeName2;
							try {
								typeName2 = dt2.resolve_type(dt2._inj().new_OS_UserType(typeName), typeName.getContext());
								// TODO transition to GenType
								clsinv.set(i, gp.get(i), typeName2.getResolved());
								ev.resolve(Operation.success(clsinv));
							} catch (ResolveError aResolveError) {
								//ev.reject( Operation.failure(aResolveError)); // !!
								ev.resolve(Operation.failure(aResolveError));
							}
						}
					} else {
						Exception genericLaziness = new Exception("1544 GenericPart does not resolve");
						ev.resolve(Operation.failure(genericLaziness));
					}
				}
			});
		} else {
			Exception genericLaziness = new Exception("1551 No generic part");
			ev.resolve(Operation.failure(genericLaziness));
		}

		return EventualExtract.of(ev);
	}

	public static DeduceTypes2Injector.DerivedClassInvocation2 withGenericPart2(final ClassStatement aClassStatement,
			final String aO, final NormalTypeName aNormalTypeName, final DeduceTypes2 aDeduceTypes2) {
		@NotNull
		final Operation<ClassInvocation> x = withGenericPart(aClassStatement, aO, aNormalTypeName, aDeduceTypes2);
		final DeduceTypes2Injector.DerivedClassInvocation2 result = new DeduceTypes2Injector.DerivedClassInvocation2() {
			@Override
			public void into(final Eventual<IInvocation> ev) {
				switch (x.mode()) {
				case SUCCESS -> {
					ev.resolve(x.success());
				}
				case FAILURE -> {
					final Throwable           failure = x.failure();
					final ExceptionDiagnostic ed      = new ExceptionDiagnostic(failure);
					ev.reject(ed);
				}
				default -> {
					// hmm!
					final Throwable           failure = new IllegalStateException("Unexpected value: " + x.mode());
					final ExceptionDiagnostic ed      = new ExceptionDiagnostic(failure);
					ev.reject(ed);
				}
				}
			}
		};
		return result;
	}
}
