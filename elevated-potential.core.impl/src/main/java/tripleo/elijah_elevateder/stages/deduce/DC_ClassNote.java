package tripleo.elijah_elevateder.stages.deduce;

import lombok.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.stages.gen_fn.IdentTableEntry;

public class DC_ClassNote {
	@Getter
	static class DC_ClassNote_DT2 {
		private final DeduceTypes2    deduceTypes2;
		private final BaseEvaFunction generatedFunction;
		private final IdentTableEntry ite;

		public DC_ClassNote_DT2(final IdentTableEntry aIte, final BaseEvaFunction aGeneratedFunction,
				final DeduceTypes2 aDeduceTypes2) {
			ite = aIte;
			generatedFunction = aGeneratedFunction;
			deduceTypes2 = aDeduceTypes2;
		}

	}

	private final DeduceCentral central;
	private final Context ctx;
	private final ClassStatement e;

	private DC_ClassNote_DT2 dc_classNote_dt2;

	public DC_ClassNote(final ClassStatement aE, final Context aCtx, final DeduceCentral aCentral) {
		e = aE;
		ctx = aCtx;
		central = aCentral;
	}

	public void attach(final IdentTableEntry aIte, final BaseEvaFunction aGeneratedFunction) {
		final DeduceTypes2 deduceTypes2 = central.getDeduceTypes2();
		dc_classNote_dt2 = deduceTypes2._inj().new_DC_ClassNote_DT2(aIte, aGeneratedFunction, deduceTypes2);
	}
}
