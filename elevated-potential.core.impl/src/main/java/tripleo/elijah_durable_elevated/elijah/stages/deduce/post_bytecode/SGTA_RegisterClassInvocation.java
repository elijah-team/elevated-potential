package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenType;

public class SGTA_RegisterClassInvocation implements setup_GenType_Action {

	private final ClassStatement classStatement;
	private final DeducePhase phase;

	public SGTA_RegisterClassInvocation(final ClassStatement aClassStatement, final DeducePhase aPhase) {
		classStatement = aClassStatement;
		phase = aPhase;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		@Nullable ClassInvocation ci = new ClassInvocation(classStatement, null, arena::getDeduceTypes2);
		ci = phase.registerClassInvocation(ci);

		arena.put("ci", ci);
	}
}
