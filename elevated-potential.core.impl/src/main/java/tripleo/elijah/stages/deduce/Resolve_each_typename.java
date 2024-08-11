package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;

class Resolve_each_typename {

	private final DeduceTypes2 deduceTypes2;
	private final DeduceTypes2 dt2;
	private final ErrSink errSink;
	private final DeducePhase phase;

	public Resolve_each_typename(final DeduceTypes2 aDeduceTypes2, final DeducePhase aPhase,
			final DeduceTypes2 aDeduceTypes2,
			final ErrSink aErrSink) {
		deduceTypes2 = aDeduceTypes2;
		phase = aPhase;
		dt2 = aDeduceTypes2;
		errSink = aErrSink;
	}

	public void action(@NotNull final TypeTableEntry typeTableEntry) {
		@Nullable
		final OS_Type attached = typeTableEntry.getAttached();
		if (attached == null)
			return;
		if (attached.getType() == OS_Type.Type.USER) {
			action_USER(typeTableEntry, attached);
		} else if (attached.getType() == OS_Type.Type.USER_CLASS) {
			action_USER_CLASS(typeTableEntry, attached);
		}
	}

	public void action_USER(@NotNull final TypeTableEntry typeTableEntry, @Nullable final OS_Type aAttached) {
		final TypeName tn = aAttached.getTypeName();
		if (tn == null)
			return; // hack specifically for Result
		switch (tn.kindOfType()) {
			case FUNCTION:
			case GENERIC:
			case TYPE_OF:
				return;
		}
		try {
			typeTableEntry.setAttached(dt2.resolve_type(aAttached, aAttached.getTypeName().getContext()));
			switch (typeTableEntry.getAttached().getType()) {
				case USER_CLASS:
					action_USER_CLASS(typeTableEntry, typeTableEntry.getAttached());
					break;
				case GENERIC_TYPENAME:
					deduceTypes2.LOG.err(String.format("801 Generic Typearg %s for %s", tn,
							"genericFunction.getFD().getParent()"));
					break;
				default:
					deduceTypes2.LOG.err("245 typeTableEntry attached wrong type " + typeTableEntry);
					break;
			}
		} catch (final ResolveError aResolveError) {
			deduceTypes2.LOG.err("288 Failed to resolve type " + aAttached);
			errSink.reportDiagnostic(aResolveError);
		}
	}

	public void action_USER_CLASS(@NotNull final TypeTableEntry typeTableEntry, @NotNull final OS_Type aAttached) {
		final ClassStatement c = aAttached.getClassOf();
		assert c != null;
		phase.onClass(c, typeTableEntry::resolve);
	}
}
