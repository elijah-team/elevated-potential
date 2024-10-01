package tripleo.elijah_elevateder.comp.nextgen.pn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.pn.SC_Fai;
import tripleo.elijah_elevated_durable.pipelines.write.WP_State_Control;

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
