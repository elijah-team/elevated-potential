package tripleo.elijah.comp.nextgen.pn;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.pn.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;

import java.io.*;

// Factory
public final class SC_Fai_ {
	private SC_Fai_() {
	}

	@NotNull
	public static SC_Fai f(final @NotNull WP_State_Control sc, final Exception aE) {
		return new SC_Fai() {
			@Override
			public void signal() {
				sc.exception(aE);
			}

			@Override
			public String sc_fai_asString() {
				return aE.toString();
			}
		};
	}
}
