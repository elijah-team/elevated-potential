package tripleo.elijah.stages.deduce;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.Context;

@Getter
public class DeduceCentral {
	private final DeduceTypes2 deduceTypes2;

	public DeduceCentral(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	public @NotNull DC_ClassNote note_Class(final ClassStatement aE, final Context aCtx) {
		DC_ClassNote cn = deduceTypes2._inj().new_DC_ClassNote(aE, aCtx, this);
		return cn;
	}

	public DeduceTypes2 getDeduceTypes2() {
		// 24/01/04 back and forth
		return this.deduceTypes2;
	}
}
