package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_durable_elevated.elijah.DebugFlags;
import tripleo.elijah_durable_elevated.elijah.lang.impl.LangGlobals;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.ProcTableEntry;

import java.util.*;
import java.util.function.Consumer;

class lfoe_action__FunctionInvocationDoneCallback implements DoneCallback<FunctionInvocation> {
	private final          DeduceElement3_ProcTableEntry deduceElement3ProcTableEntry;
	private final @NotNull DeduceTypes2                  deduceTypes2;
	private final @NotNull Consumer<WorkList>            addJobs;
	private final          __LFOE_Q                      q;
	private final @NotNull WorkList                      wl;

	public lfoe_action__FunctionInvocationDoneCallback(final DeduceElement3_ProcTableEntry aDeduceElement3ProcTableEntry, final @NotNull DeduceTypes2 aDeduceTypes2, final @NotNull Consumer<WorkList> aAddJobs, final __LFOE_Q aQ, final @NotNull WorkList aWl) {
		deduceElement3ProcTableEntry = aDeduceElement3ProcTableEntry;
		deduceTypes2                 = aDeduceTypes2;
		addJobs                      = aAddJobs;
		q                            = aQ;
		wl                           = aWl;
	}

	@Override
	public void onDone(final FunctionInvocation fi) {
		if (fi.getFunction() == null) {
			if (fi.pte == null) {
				return;
			} else {
				//					LOG.err("592 " + fi.getClassInvocation());
				if (fi.pte.getClassInvocation() != null)
					fi.setClassInvocation(fi.pte.getClassInvocation());
				//					else
				//						fi.pte.setClassInvocation(fi.getClassInvocation());
			}
		}

		@Nullable
		ClassInvocation ci = fi.getClassInvocation();
		if (ci == null) {
			ci = fi.pte.getClassInvocation();
		}
		FunctionDef fd3 = fi.getFunction();

		final ProcTableEntry principal = deduceElement3ProcTableEntry.getTablePrincipal();
		final DeduceTypes2.DeduceTypes2Injector deduceTypes2Injector = deduceElement3ProcTableEntry._inj();

		if (fd3 == LangGlobals.defaultVirtualCtor) {
			if (ci == null) {
				if (/* fi.getClassInvocation() == null && */ fi.getNamespaceInvocation() == null) {
					// Assume default constructor
					ci = deduceTypes2.phase.registerClassInvocation((ClassStatement) principal.getResolvedElement(), deduceTypes2);
					fi.setClassInvocation(ci);
				} else
					throw new NotImplementedException();
			}
			final ClassStatement klass = ci.getKlass();

			Collection<ConstructorDef> cis = klass.getConstructors();

			if (DebugFlags.FORCE_IGNORE) {
				for (@NotNull ConstructorDef constructorDef : cis) {
					final Iterable<FormalArgListItem> constructorDefArgs = constructorDef.getArgs();

					if (!constructorDefArgs.iterator().hasNext()) { // zero-sized arg list
						fd3 = constructorDef;
						break;
					}
				}
			}

			final Optional<ConstructorDef> ocd = cis.stream()
					.filter(acd -> acd.getArgs().iterator().hasNext())
					.findFirst();
			if (ocd.isPresent()) {
				fd3 = ocd.get();
			}
		}

		final OS_Element parent;
		if (fd3 != null) {
			parent = fd3.getParent();
			if (parent instanceof final ClassStatement parentClass) {
				if (ci != principal.getClassInvocation()) {
					ci = deduceTypes2Injector.new_ClassInvocation(parentClass, null,
																  new ReadySupplier_1<>(deduceElement3ProcTableEntry.deduceTypes2()));
					{
						final ClassInvocation classInvocation = principal.getClassInvocation();
						if (classInvocation != null) {
							Map<TypeName, OS_Type> gp = classInvocation.genericPart().getMap();
							if (gp != null) {
								int i = 0;
								for (Map.@NotNull Entry<TypeName, OS_Type> entry : gp.entrySet()) {
									ci.set(i, entry.getKey(), entry.getValue());
									i++;
								}
							}
						}
					}
				}
				deduceElement3ProcTableEntry.__lfoe_action__proceed(new DeduceElement3_ProcTableEntry._0(fi, ci, parentClass, deduceTypes2.phase, addJobs, q));
			} else if (parent instanceof final NamespaceStatement parentNamespace) {
				deduceElement3ProcTableEntry.__lfoe_action__proceed(new DeduceElement3_ProcTableEntry._1(fi, parentNamespace, wl, deduceTypes2.phase, addJobs, q));
			}
		} else {
			parent = ci.getKlass();
			{
				final ClassInvocation classInvocation = principal.getClassInvocation();
				if (classInvocation != null && classInvocation.genericPart().hasGenericPart()) {
					Map<TypeName, OS_Type> gp = classInvocation.genericPart().getMap();
					int                    i  = 0;
					for (Map.@NotNull Entry<TypeName, OS_Type> entry : gp.entrySet()) {
						ci.set(i, entry.getKey(), entry.getValue());
						i++;
					}
				}
			}
			deduceElement3ProcTableEntry.__lfoe_action__proceed(new DeduceElement3_ProcTableEntry._0(fi, ci, (ClassStatement) parent, deduceTypes2.phase, addJobs, q));
		}
	}
}
